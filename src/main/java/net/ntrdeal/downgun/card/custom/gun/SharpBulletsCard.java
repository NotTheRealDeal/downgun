package net.ntrdeal.downgun.card.custom.gun;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.ntrdeal.downgun.card.Card;
import org.jetbrains.annotations.Nullable;

public class SharpBulletsCard implements Card {
    @Override
    public float damageModifier(PlayerEntity player, @Nullable Entity target, float damage, double distance, int level) {
        return 4 * level;
    }
}
