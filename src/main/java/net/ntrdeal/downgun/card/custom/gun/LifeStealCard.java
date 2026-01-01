package net.ntrdeal.downgun.card.custom.gun;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.ntrdeal.downgun.card.Card;
import net.ntrdeal.downgun.entity.custom.BulletEntity;
import org.apache.commons.lang3.mutable.MutableFloat;

public class LifeStealCard implements Card {
    @Override
    public void outDamageMultiplier(BulletEntity.DamageData data, MutableFloat multiplier, int level) {
        multiplier.subtract(0.25f * level);
    }

    @Override
    public void postHit(PlayerEntity player, Entity target, float damage, int level) {
        player.heal(level * 2);
    }
}
