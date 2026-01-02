package net.ntrdeal.downgun.card.custom.gun;

import net.ntrdeal.downgun.card.Card;
import net.ntrdeal.downgun.entity.custom.BulletEntity;
import org.apache.commons.lang3.mutable.MutableFloat;

public class HealingBulletsCard implements Card {
    @Override
    public int layer() {
        return 1;
    }

    @Override
    public void outDamageMultiplier(BulletEntity.DamageData data, MutableFloat multiplier, int level) {
        if (data.teammate) multiplier.setValue(0f);
    }

    @Override
    public void postHit(BulletEntity.DamageData data, int level) {
        if (data.teammate) data.target.heal(level * 2f);
    }
}
