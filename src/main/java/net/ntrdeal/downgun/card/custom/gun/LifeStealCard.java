package net.ntrdeal.downgun.card.custom.gun;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.ntrdeal.downgun.card.Card;
import org.jetbrains.annotations.Nullable;

public class LifeStealCard implements Card {
    @Override
    public float damageModifier(PlayerEntity player, @Nullable Entity target, float damage, double distance, int level) {
        return -3 * level;}

    @Override
    public void postHit(PlayerEntity player, Entity target, float damage, int level) {
        player.heal(level * 2);
    }
}
