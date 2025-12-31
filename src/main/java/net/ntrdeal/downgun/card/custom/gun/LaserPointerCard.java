package net.ntrdeal.downgun.card.custom.gun;

import net.minecraft.entity.player.PlayerEntity;
import net.ntrdeal.downgun.card.Card;

public class LaserPointerCard implements Card {
    @Override
    public float divergenceModifier(PlayerEntity player, float divergence, int level) {
        return (divergence * 0.75f) * -level;
    }
}
