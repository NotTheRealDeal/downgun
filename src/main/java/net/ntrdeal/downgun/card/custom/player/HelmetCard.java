package net.ntrdeal.downgun.card.custom.player;

import net.ntrdeal.downgun.card.Card;
import net.ntrdeal.downgun.entity.custom.DamageData;
import org.apache.commons.lang3.mutable.MutableFloat;

public class HelmetCard implements Card {
    @Override
    public void inHeadshotMultiplier(DamageData data, MutableFloat headshotMulti, int level) {
        headshotMulti.subtract(0.35f * level);
    }
}
