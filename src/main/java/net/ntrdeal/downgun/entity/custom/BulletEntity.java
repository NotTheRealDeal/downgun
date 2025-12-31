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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.ntrdeal.downgun.card.Card;
import net.ntrdeal.downgun.entity.ModEntities;
import net.ntrdeal.downgun.misc.CardHolder;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.Map;

public class BulletEntity extends PersistentProjectileEntity {
    public static final Comparator<Map.Entry<Card, Integer>> SORTER = Comparator.comparingInt(entry -> entry.getKey().layer());
    public Vec3d startingPos = Vec3d.ZERO;

    public BulletEntity(EntityType<BulletEntity> type, World world) {
        super(type, world);
        this.pickupType = PickupPermission.DISALLOWED;
    }

    public BulletEntity(double x, double y, double z, World world) {
        super(ModEntities.BULLET_ENTITY, x, y, z, world, Items.IRON_NUGGET.getDefaultStack(), Items.IRON_NUGGET.getDefaultStack());
        this.pickupType = PickupPermission.DISALLOWED;
        this.startingPos = new Vec3d(x, y, z);
    }

    public BulletEntity(LivingEntity owner, World world, @Nullable ItemStack shotFrom) {
        super(ModEntities.BULLET_ENTITY, owner, world, Items.IRON_NUGGET.getDefaultStack(), shotFrom);
        this.pickupType = PickupPermission.DISALLOWED;
        this.startingPos = new Vec3d(owner.getX(), owner.getEyeY() - 0.1F, owner.getZ());
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        DamageSource damageSource = this.getDamageSources().arrow(this, this.getOwner());
        ServerWorld world = this.getWorld() instanceof ServerWorld serverWorld ? serverWorld : null;
        float damage = this.getBulletDamage(entity, entityHitResult.getPos());
        if (world != null) damage = EnchantmentHelper.getDamage(world, this.getWeaponStack(), entity, damageSource, damage);
        if (entity.damage(damageSource, damage)) {
            if (entity.getType().equals(EntityType.ENDERMAN)) return;
            if (world != null) EnchantmentHelper.onTargetDamaged(world, entity, damageSource, this.getWeaponStack());
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
    }

    public float getBulletDamage(@Nullable Entity target, Vec3d hitPos) {
        float damage = 2f;
        if (this.getOwner() instanceof PlayerEntity player && player instanceof CardHolder holder) {
            double distance = this.startingPos.distanceTo(hitPos);
            for (Map.Entry<Card, Integer> entry : holder.ntrdeal$getCards().entrySet().stream().sorted(SORTER).toList()) {
                damage += entry.getKey().damageModifier(player, target, damage, distance, entry.getValue());
            }
        }
        return damage;
    }

    @Override
    protected double getGravity() {
        double gravity = 0d;
        if (this.getOwner() instanceof PlayerEntity player && player instanceof CardHolder holder) {
            for (Card card : holder.ntrdeal$getCards().keySet()) {
                gravity += card.gravityModifier(player, gravity, holder.ntrdeal$getCards().get(card));
            }
        }
        return gravity;
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
