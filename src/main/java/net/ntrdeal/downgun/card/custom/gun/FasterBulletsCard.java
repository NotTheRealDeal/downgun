package net.ntrdeal.downgun.card.custom.gun;

import net.minecraft.entity.player.PlayerEntity;
import net.ntrdeal.downgun.card.Card;

public class FasterBulletsCard implements Card {
    @Override
    public float speedModifier(PlayerEntity player, float speed, int level) {
        return (speed * 0.5f) * level;
    }
}
