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
import net.ntrdeal.downgun.card.Card;
import net.ntrdeal.downgun.entity.custom.BulletEntity;
import net.ntrdeal.downgun.misc.CardHolder;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.List;
import java.util.Map;

public class TestingGunItem extends Item implements ProjectileItem {
    public TestingGunItem() {
        super(new Item.Settings());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!world.isClient() && player instanceof CardHolder holder) {
            MutableFloat divergence = new MutableFloat(2.5f), speed = new MutableFloat(2.5f);
            List<Map.Entry<Card, Integer>> layeredCards = holder.ntrdeal$getLayeredCards();
            layeredCards.forEach(entry -> entry.getKey().divergenceModifier(player, divergence, entry.getValue()));
            layeredCards.forEach(entry -> entry.getKey().speedModifier(player, speed, entry.getValue()));
            BulletEntity bullet = new BulletEntity(player, world, stack);
            bullet.setVelocity(player, player.getPitch(), player.getYaw(), 0f, speed.getValue(), Math.max(divergence.getValue(), 0f));
            world.spawnEntity(bullet);
        }
        return TypedActionResult.success(stack);
    }

    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        return new BulletEntity(pos.getX(), pos.getY(), pos.getZ(), world);
    }
}
