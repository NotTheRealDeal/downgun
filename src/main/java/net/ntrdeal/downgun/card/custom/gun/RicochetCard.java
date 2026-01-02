package net.ntrdeal.downgun.card.custom.gun;

import net.ntrdeal.downgun.card.Card;
import net.ntrdeal.downgun.component.CardHolderComponent;
import org.apache.commons.lang3.mutable.MutableInt;

public class RicochetCard implements Card {
    @Override
    public void bounceModifier(CardHolderComponent holder, MutableInt bounces, int level) {
        bounces.add(level);
    }
}
