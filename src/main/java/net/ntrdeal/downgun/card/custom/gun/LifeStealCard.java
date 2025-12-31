package net.ntrdeal.downgun.card.custom.gun;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.ntrdeal.downgun.card.Card;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.jetbrains.annotations.Nullable;

public class LifeStealCard implements Card {
    @Override
    public void damageModifier(PlayerEntity player, @Nullable Entity target, MutableFloat damage, double distance, int level) {
        damage.subtract(3f * level);
    }

    @Override
    public void postHit(PlayerEntity player, Entity target, float damage, int level) {
        player.heal(level * 2);
    }
}
