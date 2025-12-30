package net.ntrdeal.downgun.registry;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.ntrdeal.downgun.DownGun;
import net.ntrdeal.downgun.card.Card;

public class ModRegistryKeys {
    public static final RegistryKey<Registry<Card>> CARDS = key("card");

    public static <T> RegistryKey<Registry<T>> key(String name) {
        return RegistryKey.ofRegistry(DownGun.id(name));
    }
}
