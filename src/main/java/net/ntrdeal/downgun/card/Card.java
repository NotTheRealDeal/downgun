package net.ntrdeal.downgun.card;

import com.mojang.serialization.Codec;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.ntrdeal.downgun.registry.ModRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public interface Card {
    Codec<Map<Card, Integer>> CARD_MAP_CODEC = Codec.unboundedMap(ModRegistries.CARDS_CODEC, Codec.INT).xmap(HashMap::new, Map::copyOf);

    default int layer() {
        return 1;
    }

    default void tick(PlayerEntity player, int level) {}

    default void postHit(PlayerEntity player, Entity target, int level) {}

    default float damageModifier(PlayerEntity player, @Nullable Entity target, float damage, double distance, int level) {
        return 0f;
    }

    default double gravityModifier(PlayerEntity player, double gravity, int level) {
        return 0d;
    }

    default float divergenceModifier(PlayerEntity player, float divergence, int level) {
        return 0f;
    }

    default float speedModifier(PlayerEntity player, float speed, int level) {
        return 0f;
    }
}
