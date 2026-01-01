package net.ntrdeal.downgun.card.custom.gun;

import net.ntrdeal.downgun.card.Card;
import net.ntrdeal.downgun.entity.custom.BulletEntity;
import org.apache.commons.lang3.mutable.MutableFloat;

public class SharpBulletsCard implements Card {
    @Override
    public void outDamageMultiplier(BulletEntity.DamageData data, MutableFloat multiplier, int level) {
        multiplier.add(0.25f * level);
    }
}
