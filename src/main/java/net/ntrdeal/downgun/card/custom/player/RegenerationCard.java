package net.ntrdeal.downgun.card.custom.player;

import net.ntrdeal.downgun.card.Card;
import net.ntrdeal.downgun.component.CardHolderComponent;

public class RegenerationCard implements Card {
    @Override
    public void tick(CardHolderComponent holder, int level) {
        holder.player.heal(level / 30f);
    }
}
