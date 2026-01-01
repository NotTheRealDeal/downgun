package net.ntrdeal.downgun.card.custom.gun;

import net.minecraft.entity.player.PlayerEntity;
import net.ntrdeal.downgun.card.Card;
import net.ntrdeal.downgun.entity.custom.BulletEntity;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableFloat;

public class HeavyBulletsCard implements Card {
    @Override
    public void outDamageMultiplier(BulletEntity.DamageData data, MutableFloat multiplier, int level) {
        multiplier.add(0.25f * level);
    }

    @Override
    public void gravityModifier(PlayerEntity player, MutableDouble gravity, int level) {
        gravity.add(0.05d * level);
    }
}
