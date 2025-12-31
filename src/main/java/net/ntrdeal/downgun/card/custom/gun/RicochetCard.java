package net.ntrdeal.downgun.card.custom.gun;

import net.minecraft.entity.player.PlayerEntity;
import net.ntrdeal.downgun.card.Card;

public class RicochetCard implements Card {
    @Override
    public int bounceModifier(PlayerEntity player, int bounces, int level) {
        return level;
    }
}
