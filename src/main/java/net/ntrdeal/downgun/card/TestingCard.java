package net.ntrdeal.downgun.card;

import net.minecraft.entity.player.PlayerEntity;

public class TestingCard implements Card{
    @Override
    public void tick(PlayerEntity player, int level) {
        player.damage(player.getDamageSources().wither(), level);
    }
}
