package net.ntrdeal.downgun.card;

import com.mojang.serialization.Codec;
import net.minecraft.entity.player.PlayerEntity;
import net.ntrdeal.downgun.registry.ModRegistries;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public interface Card {
    Comparator<Card> SORTED = Comparator.comparing(ModRegistries.CARDS::getRawId);
    Codec<Map<Card, Integer>> CARD_MAP_CODEC = Codec.unboundedMap(ModRegistries.CARDS_CODEC, Codec.INT).xmap(cards -> {
        Map<Card, Integer> map = new TreeMap<>(SORTED);
        map.putAll(cards);
        return map;
    }, Map::copyOf);



//    Codec<Map<Card, Integer>> CARD_SET_CODEC = ModRegistries.CARDS_CODEC.listOf().xmap(cards -> {
//        Set<Card> set = new TreeSet<>(SORTED);
//        set.addAll(cards);
//        return set;
//    },);

    default void tick(PlayerEntity player, int level) {}
}
