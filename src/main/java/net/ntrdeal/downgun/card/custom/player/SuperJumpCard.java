package net.ntrdeal.downgun.card.custom.player;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.ntrdeal.downgun.DownGun;
import net.ntrdeal.downgun.card.AttributeCard;

public class SuperJumpCard extends AttributeCard {
    public SuperJumpCard() {
        this.addAttribute(EntityAttributes.GENERIC_JUMP_STRENGTH, new AttributeInstance(
                DownGun.id("super_jump"),
                (player, level) -> level.doubleValue() / 5d,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        ));
    }
}
