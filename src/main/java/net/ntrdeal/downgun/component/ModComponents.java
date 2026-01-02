package net.ntrdeal.downgun.component;

import net.ntrdeal.downgun.DownGun;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class ModComponents implements EntityComponentInitializer {
    public static final ComponentKey<CardHolderComponent> CARD_HOLDER = ComponentRegistry.getOrCreate(DownGun.id("card_holder"), CardHolderComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(CARD_HOLDER, CardHolderComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}
