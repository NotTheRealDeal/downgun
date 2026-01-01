package net.ntrdeal.downgun.card.custom.player;

import net.ntrdeal.downgun.card.Card;
import net.ntrdeal.downgun.entity.custom.BulletEntity;
import org.apache.commons.lang3.mutable.MutableFloat;

public class HelmetCard implements Card {
    @Override
    public void inHeadshotMultiplier(BulletEntity.DamageData data, MutableFloat headshotMulti, int level) {
        headshotMulti.subtract(0.35f * level);
    }
}
