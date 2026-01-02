package net.ntrdeal.downgun.card.custom.gun;

import net.ntrdeal.downgun.card.Card;
import net.ntrdeal.downgun.component.CardHolderComponent;
import net.ntrdeal.downgun.entity.custom.BulletEntity;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;

public class MultishotCard implements Card {
    @Override
    public void outDamageMultiplier(BulletEntity.DamageData data, MutableFloat multiplier, int level) {
        multiplier.subtract(0.15f * level);
    }

    @Override
    public void shootCountModifier(CardHolderComponent holder, MutableInt count, int level) {
        count.add(level);
    }

    @Override
    public void divergenceModifier(CardHolderComponent holder, MutableFloat divergence, int level) {
        divergence.add(0.5f * level);
    }
}
