package net.ntrdeal.downgun.card.custom.player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.ntrdeal.downgun.card.Card;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.jetbrains.annotations.Nullable;

public class HelmetCard implements Card {
    @Override
    public void incomingDamageModifier(PlayerEntity target, @Nullable Entity attacker, MutableFloat damage, double distance, boolean headshot, int level) {
        if (headshot) damage.subtract((damage.getValue() * 0.3f) * level);
    }
}
