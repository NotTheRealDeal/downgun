package net.ntrdeal.downgun.card.custom.gun;

import net.minecraft.entity.player.PlayerEntity;
import net.ntrdeal.downgun.card.Card;
import org.apache.commons.lang3.mutable.MutableFloat;

public class FasterBulletsCard implements Card {
    @Override
    public void speedModifier(PlayerEntity player, MutableFloat speed, int level) {
        speed.add((speed.getValue() * 0.3f) * level);
    }
}
