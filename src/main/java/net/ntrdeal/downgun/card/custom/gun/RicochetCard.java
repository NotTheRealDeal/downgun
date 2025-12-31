package net.ntrdeal.downgun.card.custom.gun;

import net.minecraft.entity.player.PlayerEntity;
import net.ntrdeal.downgun.card.Card;
import org.apache.commons.lang3.mutable.MutableInt;

public class RicochetCard implements Card {
    @Override
    public void bounceModifier(PlayerEntity player, MutableInt bounces, int level) {
        bounces.add(level);
    }
}
