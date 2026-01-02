package net.ntrdeal.downgun.card.custom.gun;

import net.ntrdeal.downgun.card.Card;
import net.ntrdeal.downgun.card.ModCards;
import net.ntrdeal.downgun.entity.custom.BulletEntity;

public class FlameOnCard implements Card {
    @Override
    public void postHit(BulletEntity.DamageData data, int level) {
        if (!data.teammate || !data.attackerHas(ModCards.HEALING_BULLETS)) data.target.setOnFireFor(4f * level);
    }
}
