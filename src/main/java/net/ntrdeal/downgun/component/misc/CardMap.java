package net.ntrdeal.downgun.component.misc;

import com.mojang.serialization.Codec;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.ntrdeal.downgun.card.Card;
import net.ntrdeal.downgun.registry.ModRegistries;

import java.util.*;

public class CardMap extends HashMap<Card, Integer> {
    public static final Codec<CardMap> CODEC = Codec.unboundedMap(ModRegistries.CARDS.getCodec(), Codec.INT).xmap(CardMap::new, map -> map);

    public CardMap(Map<Card, Integer> starting) {
        this(); this.putAll(starting);
    }

    public CardMap() {
        super();
    }

    public boolean addCard(PlayerEntity player, Card card, int level) {
        Integer currentLevel = this.get(card);
        if (currentLevel == null || currentLevel != level) {
            this.put(card, level);
            card.onApplied(player, level);
            return true;
        } else return false;
    }

    public boolean removeCard(PlayerEntity player, Card card) {
        if (this.remove(card) instanceof Integer level) {
            card.onRemoval(player, level);
            return true;
        } else return false;
    }

    public boolean resetCards(PlayerEntity player) {
        if (this.isEmpty()) return false;
        else {
            this.forEach((card, level) -> this.removeCard(player, card));
            return true;
        }
    }

    public List<Entry<Card, Integer>> layeredCards() {
        return this.entrySet().stream().sorted(Comparator.comparing(entry -> entry.getKey().layer())).toList();
    }

    public Set<RegistryKey<Card>> getCardKeys() {
        Set<RegistryKey<Card>> set = new HashSet<>();
        this.keySet().forEach(card -> ModRegistries.CARDS.getKey(card).ifPresent(set::add));
        return set;
    }
}
