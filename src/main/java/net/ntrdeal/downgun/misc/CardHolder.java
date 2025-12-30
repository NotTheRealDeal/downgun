package net.ntrdeal.downgun.misc;

import net.ntrdeal.downgun.card.Card;

import java.util.Map;
import java.util.TreeMap;

public interface CardHolder {
    void ntrdeal$setCards(Map<Card, Integer> cards);
    Map<Card, Integer> ntrdeal$getCards();

    default boolean ntrdeal$AddCard(Card card, int level) {
        Integer currentLevel = this.ntrdeal$getCards().get(card);
        if (currentLevel == null || currentLevel != level) {
            this.ntrdeal$getCards().put(card, level);
            return true;
        } else return false;
    }

    default boolean ntrdeal$resetCards() {
        if (this.ntrdeal$getCards().isEmpty()) return false;
        else {
            this.ntrdeal$setCards(new TreeMap<>(Card.SORTED));
            return true;
        }
    }
}
