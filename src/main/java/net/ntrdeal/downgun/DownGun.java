package net.ntrdeal.downgun;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.ntrdeal.downgun.card.ModCards;
import net.ntrdeal.downgun.misc.CardHolder;
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

		Registry.register(Registries.ITEM, id("testing"), new Item(new Item.Settings()){
			@Override
			public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
				if (player instanceof CardHolder holder && holder.ntrdeal$AddCard(ModCards.TESTING_CARD, 1)) return TypedActionResult.success(player.getStackInHand(hand));
				return super.use(world, player, hand);
			}
		});

		Registry.register(Registries.ITEM, id("clearer"), new Item(new Item.Settings()){
			@Override
			public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
				if (player instanceof CardHolder holder && holder.ntrdeal$resetCards()) return TypedActionResult.success(player.getStackInHand(hand));
				return super.use(world, player, hand);
			}
		});
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}