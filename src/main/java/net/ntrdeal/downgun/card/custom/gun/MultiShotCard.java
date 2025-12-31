package net.ntrdeal.downgun.card.custom.gun;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.ntrdeal.downgun.card.Card;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.Nullable;

public class MultiShotCard implements Card {
    @Override
    public void damageModifier(PlayerEntity player, @Nullable Entity target, MutableFloat damage, double distance, int level) {
        damage.subtract(2f * level);
    }
    @Override
    public void shootCountModifier(PlayerEntity player, MutableInt count, int level) {
        count.add(level);
    }
}
