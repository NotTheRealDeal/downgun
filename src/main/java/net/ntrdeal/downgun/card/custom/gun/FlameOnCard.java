package net.ntrdeal.downgun.card.custom.gun;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.ntrdeal.downgun.card.Card;

public class FlameOnCard implements Card {
    @Override
    public void postHit(PlayerEntity player, Entity target, float damage, int level) {
        target.setOnFireFor(2f * level);
    }
}
