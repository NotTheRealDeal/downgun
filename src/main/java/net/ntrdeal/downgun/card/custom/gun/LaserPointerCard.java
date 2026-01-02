package net.ntrdeal.downgun.card.custom.gun;

import net.ntrdeal.downgun.card.Card;
import net.ntrdeal.downgun.component.CardHolderComponent;
import org.apache.commons.lang3.mutable.MutableFloat;

public class LaserPointerCard implements Card {
    @Override
    public void divergenceModifier(CardHolderComponent holder, MutableFloat divergence, int level) {
        divergence.subtract((divergence.getValue() * 0.75f) * level);
    }
}
