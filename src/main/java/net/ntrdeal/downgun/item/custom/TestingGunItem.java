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
import org.apache.commons.lang3.mutable.MutableInt;

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
            List<Map.Entry<Card, Integer>> layeredCards = holder.ntrdeal$getLayeredCards();
            MutableFloat divergence = new MutableFloat(2.5f), speed = new MutableFloat(25f), gravity = new MutableFloat(0f);
            MutableInt maxBounces = new MutableInt(0);
            layeredCards.forEach(entry -> entry.getKey().divergenceModifier(player, divergence, entry.getValue()));
            layeredCards.forEach(entry -> entry.getKey().speedModifier(player, speed, entry.getValue()));
            layeredCards.forEach(entry -> entry.getKey().gravityModifier(player, gravity, entry.getValue()));
            layeredCards.forEach(entry -> entry.getKey().bounceModifier(player, maxBounces, entry.getValue()));
            world.spawnEntity(new BulletEntity(player, world, stack, speed.getValue(), gravity.getValue(), maxBounces.getValue(), Math.max(divergence.getValue(), 0f), false));
        }
        return TypedActionResult.success(stack);
    }

    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        return new BulletEntity(pos.getX(), pos.getY(), pos.getZ(), world);
    }
}
