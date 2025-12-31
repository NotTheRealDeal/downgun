package net.ntrdeal.downgun.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.ntrdeal.downgun.DownGun;
import net.ntrdeal.downgun.item.custom.TestingGunItem;

public class ModItems {
    public static final TestingGunItem GUN = register("gun", new TestingGunItem());

    public static <T extends Item> T register(String name, T item) {
        return Registry.register(Registries.ITEM, DownGun.id(name), item);
    }

    public static void register() {}
}
