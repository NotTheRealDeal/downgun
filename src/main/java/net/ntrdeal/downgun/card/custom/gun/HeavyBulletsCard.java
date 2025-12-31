package net.ntrdeal.downgun.card.custom.gun;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.ntrdeal.downgun.card.Card;
import org.jetbrains.annotations.Nullable;

public class HeavyBulletsCard implements Card {
    @Override
    public float damageModifier(PlayerEntity player, @Nullable Entity target, float damage, double distance, int level) {
        return (damage * 0.5f) * level;
    }

    @Override
    public double gravityModifier(PlayerEntity player, double gravity, int level) {
        return 0.05d * level;
    }
}
