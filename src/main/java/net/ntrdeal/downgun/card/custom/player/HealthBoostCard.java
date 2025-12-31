package net.ntrdeal.downgun.card.custom.player;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.ntrdeal.downgun.DownGun;
import net.ntrdeal.downgun.card.AttributeCard;

public class HealthBoostCard extends AttributeCard {
    public HealthBoostCard() {
        this.addAttribute(EntityAttributes.GENERIC_MAX_HEALTH, new AttributeInstance(
                DownGun.id("health_boost"),
                (player, level) -> level * 40d,
                EntityAttributeModifier.Operation.ADD_VALUE
        ));
    }
}
