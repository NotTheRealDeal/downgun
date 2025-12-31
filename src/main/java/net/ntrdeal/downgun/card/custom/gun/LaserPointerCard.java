package net.ntrdeal.downgun.card.custom.gun;

import net.minecraft.entity.player.PlayerEntity;
import net.ntrdeal.downgun.card.Card;
import org.apache.commons.lang3.mutable.MutableFloat;

public class LaserPointerCard implements Card {
    @Override
    public void divergenceModifier(PlayerEntity player, MutableFloat divergence, int level) {
        divergence.subtract((divergence.getValue() * 0.75f) * level);
    }
}
