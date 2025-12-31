package net.ntrdeal.downgun.misc;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.world.GameRules;

public class ModEvents {
    public static void register() {
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            if (oldPlayer instanceof CardHolder oldHolder && newPlayer instanceof CardHolder newHolder) {
                newHolder.ntrdeal$setCards(oldHolder.ntrdeal$getCards());
            }
        });

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            server.getGameRules().get(GameRules.FALL_DAMAGE).set(false, server);
        });
    }
}
