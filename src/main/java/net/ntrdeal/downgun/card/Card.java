package net.ntrdeal.downgun.card;

import com.mojang.serialization.Codec;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.ntrdeal.downgun.registry.ModRegistries;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public interface Card {
    Codec<Map<Card, Integer>> CARD_MAP_CODEC = Codec.unboundedMap(ModRegistries.CARDS_CODEC, Codec.INT).xmap(HashMap::new, Map::copyOf);

    default int layer() {
        return 0;
    }

    default void tick(PlayerEntity player, int level) {}

    default void postHit(PlayerEntity player, Entity target, float damage, int level) {}
    default void onHit(PlayerEntity player, DamageSource source, float damage, int level) {}

    default void damageModifier(PlayerEntity player, @Nullable Entity target, MutableFloat damage, double distance, int level) {}
    default void gravityModifier(PlayerEntity player, MutableDouble gravity, int level) {}
    default void divergenceModifier(PlayerEntity player, MutableFloat divergence, int level) {}
    default void speedModifier(PlayerEntity player, MutableFloat speed, int level) {}
    default void bounceModifier(PlayerEntity player, MutableInt bounces, int level) {}
    default void shootCountModifier(PlayerEntity player, MutableInt count, int level) {}

    @Nullable
    default Map<RegistryEntry<EntityAttribute>, AttributeInstance> attributeMap() {
        return null;
    }

    default void addAttribute(RegistryEntry<EntityAttribute> attribute, AttributeInstance instance) {
        if (this.attributeMap() instanceof Map<RegistryEntry<EntityAttribute>, AttributeInstance> map) {
            map.put(attribute, instance);
        }
    }

    default void onApplied(PlayerEntity player, int level) {
        if (this.attributeMap() instanceof Map<RegistryEntry<EntityAttribute>, AttributeInstance> map) {
            for (Map.Entry<RegistryEntry<EntityAttribute>, AttributeInstance> entry : map.entrySet()) {
                EntityAttributeInstance entityAttributeInstance = player.getAttributes().getCustomInstance(entry.getKey());
                if (entityAttributeInstance != null) {
                    entityAttributeInstance.removeModifier(entry.getValue().id());
                    entityAttributeInstance.addPersistentModifier(entry.getValue().createAttributeModifier(player, level));
                }
            }
        }
    }

    default void onRemoval(PlayerEntity player, int level) {
        if (this.attributeMap() instanceof Map<RegistryEntry<EntityAttribute>, AttributeInstance> map) {
            for (Map.Entry<RegistryEntry<EntityAttribute>, AttributeInstance> entry : map.entrySet()) {
                EntityAttributeInstance entityAttributeInstance = player.getAttributes().getCustomInstance(entry.getKey());
                if (entityAttributeInstance != null) {
                    entityAttributeInstance.removeModifier(entry.getValue().id());
                }
            }
        }
    }

    record AttributeInstance(Identifier id, BiFunction<PlayerEntity, Integer, Double> amount, EntityAttributeModifier.Operation operation) {
        public EntityAttributeModifier createAttributeModifier(PlayerEntity player, int level) {
            return new EntityAttributeModifier(this.id, this.amount.apply(player, level), this.operation);
        }
    }
}
