package net.ntrdeal.downgun.card.custom.player;

import net.minecraft.entity.player.PlayerEntity;
import net.ntrdeal.downgun.card.Card;

public class RegenerationCard implements Card {
    @Override
    public void tick(PlayerEntity player, int level) {
        player.heal(level / 30f);
    }
}
