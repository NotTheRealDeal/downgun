package net.ntrdeal.downgun.card.custom.gun;

import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particle.ParticleTypes;
import net.ntrdeal.downgun.card.Card;
import net.ntrdeal.downgun.card.ModCards;
import net.ntrdeal.downgun.component.CardHolderComponent;
import net.ntrdeal.downgun.entity.custom.DamageData;
import net.ntrdeal.downgun.misc.Functions;

public class FlameOnCard implements Card {
    @Override
    public void bulletTick(CardHolderComponent component, ProjectileEntity bullet, int level) {
        Functions.spawnParticles(bullet, ParticleTypes.FLAME, 3, 0.25f, 0.25f, 0.25f, 0.05f);
    }

    @Override
    public void postHit(DamageData data, int level) {
        if (!data.teammate || !data.attackerHas(ModCards.HEALING_BULLETS)) data.target.setOnFireFor(4f * level);
    }
}
