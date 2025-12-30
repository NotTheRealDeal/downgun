package net.ntrdeal.downgun.network;

import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.network.codec.PacketCodecs;
import net.ntrdeal.downgun.card.Card;

import java.util.Map;

public class ModTrackedData {
    public static final TrackedDataHandler<Map<Card, Integer>> TRACK_CARDS = TrackedDataHandler.create(PacketCodecs.registryCodec(Card.CARD_MAP_CODEC));

    public static void register() {
        TrackedDataHandlerRegistry.register(TRACK_CARDS);
    }
}
