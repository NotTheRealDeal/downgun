package net.ntrdeal.downgun.card.custom.gun;

import net.ntrdeal.downgun.card.Card;
import net.ntrdeal.downgun.entity.custom.DamageData;
import org.apache.commons.lang3.mutable.MutableFloat;

public class SharpBulletsCard implements Card {
    @Override
    public void outDamageMultiplier(DamageData data, MutableFloat multiplier, int level) {
        multiplier.add(0.25f * level);
    }
}
