package net.ntrdeal.downgun.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.ntrdeal.downgun.card.Card;

public class ModRegistries {
    public static final Registry<Card> CARDS = FabricRegistryBuilder.createSimple(ModRegistryKeys.CARDS).buildAndRegister();

    public static void register() {}
}
