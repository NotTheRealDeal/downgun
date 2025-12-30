package net.ntrdeal.downgun.registry;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.ntrdeal.downgun.card.Card;

public class ModRegistries {
    public static final Registry<Card> CARDS = FabricRegistryBuilder.createSimple(ModRegistryKeys.CARDS).buildAndRegister();
    public static final Codec<Card> CARDS_CODEC = CARDS.getCodec();

    public static void register() {}
}
