package net.ntrdeal.downgun.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.ntrdeal.downgun.entity.custom.BulletEntity;

public class TestingGun extends Item implements ProjectileItem {
    public TestingGun() {
        super(new Item.Settings());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!world.isClient()) {
            BulletEntity bullet = new BulletEntity(player, world, stack);
            bullet.setVelocity(player, player.getPitch(), player.getYaw(), 0f, 2.5F, 1f);
            world.spawnEntity(bullet);
        }
        return TypedActionResult.success(stack);
    }

    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        return new BulletEntity(pos.getX(), pos.getY(), pos.getZ(), world);
    }
}
