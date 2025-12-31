package net.ntrdeal.downgun.card.custom.gun;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.ntrdeal.downgun.card.Card;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.jetbrains.annotations.Nullable;

public class SharpBulletsCard implements Card {
    @Override
    public void damageModifier(PlayerEntity player, @Nullable Entity target, MutableFloat damage, double distance, boolean headshot, int level) {
        damage.add(4f * level);
    }
}
