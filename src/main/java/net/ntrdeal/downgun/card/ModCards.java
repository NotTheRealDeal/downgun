package net.ntrdeal.downgun.card;

import net.minecraft.registry.Registry;
import net.ntrdeal.downgun.DownGun;
import net.ntrdeal.downgun.card.custom.gun.*;
import net.ntrdeal.downgun.card.custom.player.RegenerationCard;
import net.ntrdeal.downgun.registry.ModRegistries;

public class ModCards {
    public static final HeavyBulletsCard HEAVY_BULLETS = register("heavy_bullets", new HeavyBulletsCard());
    public static final RegenerationCard REGENERATION = register("regeneration", new RegenerationCard());
    public static final LaserPointerCard LASER_POINTER = register("laser_pointer", new LaserPointerCard());
    public static final FlameOnCard FLAME_ON = register("flame_on", new FlameOnCard());
    public static final FasterBulletsCard FASTER_BULLETS = register("faster_bullets", new FasterBulletsCard());
    public static final SharpBulletsCard SHARP_BULLETS = register("sharp_bullets", new SharpBulletsCard());

    public static <T extends Card> T register(String name, T card) {
        return Registry.register(ModRegistries.CARDS, DownGun.id(name), card);
    }

    public static void register() {}
}
