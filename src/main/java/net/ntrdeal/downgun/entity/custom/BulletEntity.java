package net.ntrdeal.downgun.entity.custom;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
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

    public float getBulletDamage(@Nullable Entity target, Vec3d hitPos) {
        MutableFloat damage = new MutableFloat(14f);
        double distance = this.startingPos.distanceTo(hitPos);
        boolean headshot = target != null && this.getBoundingBox().intersects(new Box(target.getEyePos(), target.getEyePos()).expand(target.getWidth(), 0.01f, target.getWidth()));
        if (this.getOwner() instanceof PlayerEntity player && player instanceof CardHolder holder) {
            holder.ntrdeal$getLayeredCards().forEach(entry -> entry.getKey().damageModifier(player, target, damage, distance, headshot, entry.getValue()));
        }
        if (target instanceof PlayerEntity player && player instanceof CardHolder holder) {
            holder.ntrdeal$getLayeredCards().forEach(entry -> entry.getKey().incomingDamageModifier(player, this.getOwner(), damage, distance, headshot, entry.getValue()));
        }
        System.out.println(headshot);
        return Math.max(damage.getValue(), 1f);
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
}
