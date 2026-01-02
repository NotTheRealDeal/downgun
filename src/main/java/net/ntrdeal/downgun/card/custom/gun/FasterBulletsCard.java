package net.ntrdeal.downgun.card.custom.gun;

import net.ntrdeal.downgun.card.Card;
import net.ntrdeal.downgun.component.CardHolderComponent;
import org.apache.commons.lang3.mutable.MutableFloat;

public class FasterBulletsCard implements Card {
    @Override
    public void speedModifier(CardHolderComponent holder, MutableFloat speed, int level) {
        speed.add((speed.getValue() * 0.3f) * level);
    }
}
