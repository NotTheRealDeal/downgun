package net.ntrdeal.downgun.card;

import net.minecraft.registry.Registry;
import net.ntrdeal.downgun.DownGun;
import net.ntrdeal.downgun.card.custom.gun.HeavyBulletsCard;
import net.ntrdeal.downgun.card.custom.player.RegenerationCard;
import net.ntrdeal.downgun.registry.ModRegistries;

public class ModCards {
    public static final TestingCard TESTING_CARD = register("testing", new TestingCard());
    public static final HeavyBulletsCard HEAVY_BULLETS = register("heavy_bullets", new HeavyBulletsCard());
    public static final RegenerationCard REGENERATION = register("regeneration", new RegenerationCard());

    public static <T extends Card> T register(String name, T card) {
        return Registry.register(ModRegistries.CARDS, DownGun.id(name), card);
    }

    public static void register() {}
}
