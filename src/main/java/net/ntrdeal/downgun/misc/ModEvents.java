package net.ntrdeal.downgun.misc;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameRules;
import net.ntrdeal.downgun.component.ModComponents;

public class ModEvents {
    public static void register() {
        ServerLivingEntityEvents.AFTER_DAMAGE.register((target, source, baseDamageTaken, damageTaken, blocked) -> {
            if (!blocked && target instanceof PlayerEntity player) ModComponents.CARD_HOLDER.get(player).getLayeredCards().forEach(entry -> entry.getKey().onHit(
                    player, source, damageTaken, entry.getValue()
            ));
        });

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            server.getGameRules().get(GameRules.FALL_DAMAGE).set(false, server);
        });
    }
}
