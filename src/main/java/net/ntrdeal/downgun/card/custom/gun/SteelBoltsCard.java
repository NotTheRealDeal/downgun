package net.ntrdeal.downgun.card.custom.gun;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.ntrdeal.downgun.DownGun;
import net.ntrdeal.downgun.card.AttributeCard;

public class SteelBoltsCard extends AttributeCard {
    public SteelBoltsCard() {
        this.addAttribute(EntityAttributes.GENERIC_MOVEMENT_SPEED, new AttributeInstance(
                DownGun.id("steel_bolts"),
                (player, level) -> level * 0.1d,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        ));
    }
}
