package net.ntrdeal.downgun;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.ntrdeal.downgun.card.ModCards;
import net.ntrdeal.downgun.command.ModCommands;
import net.ntrdeal.downgun.entity.ModEntities;
import net.ntrdeal.downgun.item.ModItems;
import net.ntrdeal.downgun.misc.ModEvents;
import net.ntrdeal.downgun.misc.ModGameRules;
import net.ntrdeal.downgun.network.ModTrackedData;
import net.ntrdeal.downgun.registry.ModRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownGun implements ModInitializer {
	public static final String MOD_ID = "downgun";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModRegistries.register();
		ModTrackedData.register();
		ModCards.register();
		ModEntities.register();
		ModItems.register();
		ModGameRules.register();
		ModCommands.register();
		ModEvents.register();
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}