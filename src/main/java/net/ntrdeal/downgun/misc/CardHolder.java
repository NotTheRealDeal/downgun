package net.ntrdeal.downgun.misc;

import net.minecraft.registry.RegistryKey;
import net.ntrdeal.downgun.card.Card;
import net.ntrdeal.downgun.registry.ModRegistries;

import java.util.*;

public interface CardHolder {
    void ntrdeal$setCards(Map<Card, Integer> cards);
    Map<Card, Integer> ntrdeal$getCards();

    default Map<Card, Integer> ntrdeal$getLayeredCards() {
        Map<Card, Integer> sortedMap = new TreeMap<>(Comparator.comparing(Card::layer));
        sortedMap.putAll(this.ntrdeal$getCards());
        return sortedMap;
    }

    default Set<RegistryKey<Card>> ntrdeal$getCardKeys() {
        Set<RegistryKey<Card>> cards = new HashSet<>();
        this.ntrdeal$getCards().keySet().forEach(card -> ModRegistries.CARDS.getEntry(card).getKey().ifPresent(cards::add));
        return cards;
    }

    default boolean ntrdeal$AddCard(Card card, int level) {
        Integer currentLevel = this.ntrdeal$getCards().get(card);
        if (currentLevel == null || currentLevel != level) {
            this.ntrdeal$getCards().put(card, level);
            return true;
        } else return false;
    }

    default boolean ntrdeal$removeCard(Card card) {
        return this.ntrdeal$getCards().remove(card) != null;
    }

    default boolean ntrdeal$resetCards() {
        if (this.ntrdeal$getCards().isEmpty()) return false;
        else {
            this.ntrdeal$setCards(new HashMap<>());
            return true;
        }
    }
}
