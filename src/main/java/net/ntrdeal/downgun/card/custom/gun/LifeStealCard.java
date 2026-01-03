package net.ntrdeal.downgun.card.custom.gun;

import net.ntrdeal.downgun.card.Card;
import net.ntrdeal.downgun.entity.custom.DamageData;
import org.apache.commons.lang3.mutable.MutableFloat;

public class LifeStealCard implements Card {
    @Override
    public void outDamageMultiplier(DamageData data, MutableFloat multiplier, int level) {
        multiplier.subtract(0.25f * level);
    }

    @Override
    public void postHit(DamageData data, int level) {
        data.attacker.heal(level * 2);
    }
}
