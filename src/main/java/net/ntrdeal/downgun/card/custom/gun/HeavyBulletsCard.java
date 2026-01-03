package net.ntrdeal.downgun.card.custom.gun;

import net.ntrdeal.downgun.card.Card;
import net.ntrdeal.downgun.component.CardHolderComponent;
import net.ntrdeal.downgun.entity.custom.DamageData;
import org.apache.commons.lang3.mutable.MutableFloat;

public class HeavyBulletsCard implements Card {
    @Override
    public void outDamageMultiplier(DamageData data, MutableFloat multiplier, int level) {
        multiplier.add(0.25f * level);
    }

    @Override
    public void gravityModifier(CardHolderComponent holder, MutableFloat gravity, int level) {
        gravity.add(0.025f * level);
    }
}
