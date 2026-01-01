package net.ntrdeal.downgun.entity.custom;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.ntrdeal.downgun.entity.ModDamageSources;
import net.ntrdeal.downgun.entity.ModEntities;
import net.ntrdeal.downgun.misc.CardHolder;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.Nullable;

public class BulletEntity extends PersistentProjectileEntity {
    public Vec3d startingPos = Vec3d.ZERO;
    public int bounces = 0;
    public final boolean multishotBullet;

    public BulletEntity(EntityType<BulletEntity> type, World world) {
        super(type, world);
        this.multishotBullet = true;
        this.pickupType = PickupPermission.DISALLOWED;
    }

    public BulletEntity(double x, double y, double z, World world) {
        super(ModEntities.BULLET_ENTITY, x, y, z, world, Items.IRON_NUGGET.getDefaultStack(), Items.IRON_NUGGET.getDefaultStack());
        this.multishotBullet = true;
        this.pickupType = PickupPermission.DISALLOWED;
        this.startingPos = this.getPos();
    }

    public BulletEntity(LivingEntity owner, World world, @Nullable ItemStack shotFrom, float speed, float divergence, boolean multishot) {
        super(ModEntities.BULLET_ENTITY, owner, world, Items.IRON_NUGGET.getDefaultStack(), shotFrom);
        this.multishotBullet = multishot;
        this.pickupType = PickupPermission.DISALLOWED;
        this.startingPos = this.getPos();
        this.setVelocity(owner, owner.getPitch(), owner.getYaw(), 0f, speed, divergence);

        if (!this.multishotBullet) {
            MutableInt multishotCount = new MutableInt(0);
            if (this.getOwner() instanceof PlayerEntity player && player instanceof CardHolder holder) {
                holder.ntrdeal$getLayeredCards().forEach(entry -> entry.getKey().shootCountModifier(player, multishotCount, entry.getValue()));
                for (int count = 0; count < multishotCount.getValue(); count++) {
                    world.spawnEntity(new BulletEntity(player, world, shotFrom, speed, divergence, true));
                }
            }
        }
    }

    @Override
    public void tick() {
        if (this.age >= 500) this.discard();
        super.tick();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        DamageSource damageSource = ModDamageSources.of(this).bullet(this, this.getOwner());
        ServerWorld world = this.getWorld() instanceof ServerWorld serverWorld ? serverWorld : null;
        float damage = this.getBulletDamage(entity, entityHitResult.getPos());
        Entity owner = this.getOwner();
        if (entity.damage(damageSource, damage)) {
            if (entity.getType().equals(EntityType.ENDERMAN)) return;
            if (world != null) EnchantmentHelper.onTargetDamaged(world, entity, damageSource, this.getWeaponStack());
            if (owner instanceof PlayerEntity player && player instanceof CardHolder holder) {
                holder.ntrdeal$getCards().forEach((key, value) -> key.postHit(player, entity, damage, value));
            }
        }
        this.discard();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (this.getOwner() instanceof PlayerEntity player && player instanceof CardHolder holder) {
            MutableInt maxBounces = new MutableInt(0);
            holder.ntrdeal$getLayeredCards().forEach(entry -> entry.getKey().bounceModifier(player, maxBounces, entry.getValue()));
            if (this.bounces >= maxBounces.getValue()) this.discard();
            else {
                Vec3d velocity = this.getVelocity();
                if (blockHitResult.getSide().getAxis().isHorizontal()) {
                    this.setVelocity(-velocity.getX(), velocity.getY(), -velocity.getZ());
                }
                if (blockHitResult.getSide().getAxis().isVertical()) {
                    this.setVelocity(velocity.getX(), -velocity.getY(), velocity.getZ());
                }
                this.bounces++;
            }
        } else this.discard();
    }

    public float getBulletDamage(Entity entity, Vec3d hitPos) {
        if (this.getOwner() instanceof LivingEntity attacker && entity instanceof LivingEntity target) {
            DamageData data = new DamageData(14f, 1.5f, this.startingPos, hitPos, this, attacker, target);
            return Math.max(data.getTotalDamage(), 0f);
        } else return 14f;
    }

    @Override
    protected double getGravity() {
        MutableDouble gravity = new MutableDouble(0d);
        if (this.getOwner() instanceof PlayerEntity player && player instanceof CardHolder holder) {
            holder.ntrdeal$getLayeredCards().forEach(entry -> entry.getKey().gravityModifier(player, gravity, entry.getValue()));
        }
        return gravity.getValue();
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return Items.IRON_NUGGET.getDefaultStack();
    }

    @Override
    public boolean shouldRender(double distance) {
        return true;
    }

    public static class DamageData {
        public final float damage;
        public final float headshotMultiplier;
        public final double distance;
        public final boolean headshot;
        public final LivingEntity attacker, target;

        public DamageData(float damage, float headshotMultiplier, Vec3d startPos, Vec3d endPos, ProjectileEntity projectile, LivingEntity attacker, LivingEntity target) {
            this.damage = damage;
            this.headshotMultiplier = headshotMultiplier;
            this.distance = startPos.distanceTo(endPos);
            this.headshot = projectile.getBoundingBox().intersects(new Box(target.getEyePos(), target.getEyePos()).expand(target.getWidth(), 0.1f, target.getWidth()));
            this.attacker = attacker;
            this.target = target;
        }

        public float getTotalDamage() {
            return this.getDamage() * this.getMultiplier();
        }

        public float getDamage() {
            MutableFloat damage = new MutableFloat(this.damage);
            if (this.attacker instanceof PlayerEntity player && player instanceof CardHolder holder) {
                holder.ntrdeal$getLayeredCards().forEach(entry -> entry.getKey().outDamageModifier(this, damage, entry.getValue()));
            }
            if (this.target instanceof PlayerEntity player && player instanceof CardHolder holder) {
                holder.ntrdeal$getLayeredCards().forEach(entry -> entry.getKey().inDamageModifier(this, damage, entry.getValue()));
            }
            return damage.getValue();
        }

        public float getMultiplier() {
            MutableFloat multiplier = new MutableFloat(1f), headshotMulti = new MutableFloat(this.headshot ? 1.5f : 1f);
            if (this.attacker instanceof PlayerEntity player && player instanceof CardHolder holder) {
                holder.ntrdeal$getLayeredCards().forEach(entry -> entry.getKey().outDamageMultiplier(this, multiplier, entry.getValue()));
            }
            if (this.target instanceof PlayerEntity player && player instanceof CardHolder holder) {
                holder.ntrdeal$getLayeredCards().forEach(entry -> entry.getKey().inDamageMultiplier(this, multiplier, entry.getValue()));
            }
            if (this.headshot) {
                if (this.attacker instanceof PlayerEntity player && player instanceof CardHolder holder) {
                    holder.ntrdeal$getLayeredCards().forEach(entry -> entry.getKey().outHeadshotMultiplier(this, headshotMulti, entry.getValue()));
                }
                if (this.target instanceof PlayerEntity player && player instanceof CardHolder holder) {
                    holder.ntrdeal$getLayeredCards().forEach(entry -> entry.getKey().inHeadshotMultiplier(this, headshotMulti, entry.getValue()));
                }
            }
            return multiplier.getValue() * headshotMulti.getValue();
        }
    }
}
