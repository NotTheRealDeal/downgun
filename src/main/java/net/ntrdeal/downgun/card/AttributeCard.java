package net.ntrdeal.downgun.card;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class AttributeCard implements Card {
    public final Map<RegistryEntry<EntityAttribute>, AttributeInstance> attributeMap = new HashMap<>();
    @Override public @Nullable Map<RegistryEntry<EntityAttribute>, AttributeInstance> attributeMap() { return this.attributeMap; }
}
