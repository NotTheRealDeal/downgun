package net.ntrdeal.downgun.entity.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.ntrdeal.downgun.card.Card;
import net.ntrdeal.downgun.component.CardHolderComponent;
import net.ntrdeal.downgun.component.ModComponents;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DamageData {
    public final float damage, headshotMultiplier;
    public final double distance;
    public final boolean headshot, teammate;
    public final LivingEntity attacker, target;
    public final DamageSource damageSource;

    public final @Nullable CardHolderComponent attackerHolder, targetHolder;

    public DamageData(float damage, float headshotMultiplier, @NotNull Vec3d startPos, Vec3d endPos, ProjectileEntity projectile, DamageSource source, LivingEntity attacker, LivingEntity target) {
        this.damage = damage;
        this.headshotMultiplier = headshotMultiplier;
        this.distance = startPos.distanceTo(endPos);
        this.headshot = projectile.getBoundingBox().intersects(new Box(target.getEyePos(), target.getEyePos()).expand(target.getWidth(), 0.1f, target.getWidth()));
        this.attacker = attacker;
        this.target = target;
        this.damageSource = source;

        this.teammate = this.attacker.isTeammate(this.target);

        if (this.attacker instanceof PlayerEntity player) this.attackerHolder = ModComponents.CARD_HOLDER.get(player);
        else this.attackerHolder = null;
        if (this.target instanceof PlayerEntity player) this.targetHolder = ModComponents.CARD_HOLDER.get(player);
        else this.targetHolder = null;
    }

    public float getTotalDamage() {
        return this.getDamage() * this.getMultiplier();
    }

    public float getDamage() {
        MutableFloat damage = new MutableFloat(this.damage);
        if (this.attackerHolder != null) this.attackerHolder.getLayeredCards().forEach(entry -> entry.getKey().outDamageModifier(this, damage, entry.getValue()));
        if (this.targetHolder != null) this.targetHolder.getLayeredCards().forEach(entry -> entry.getKey().inDamageModifier(this, damage, entry.getValue()));
        return damage.getValue();
    }

    public float getMultiplier() {
        MutableFloat multiplier = new MutableFloat(1f), headshotMulti = new MutableFloat(this.headshot ? 1.5f : 1f);
        if (attackerHolder != null) {
            this.attackerHolder.getLayeredCards().forEach(entry -> entry.getKey().outDamageMultiplier(this, multiplier, entry.getValue()));
            this.attackerHolder.getLayeredCards().forEach(entry -> entry.getKey().outHeadshotMultiplier(this, headshotMulti, entry.getValue()));
        }
        if (targetHolder != null) {
            this.targetHolder.getLayeredCards().forEach(entry -> entry.getKey().inDamageMultiplier(this, multiplier, entry.getValue()));
            this.targetHolder.getLayeredCards().forEach(entry -> entry.getKey().inHeadshotMultiplier(this, headshotMulti, entry.getValue()));
        }
        return multiplier.getValue() * headshotMulti.getValue();
    }

    public boolean attackerHas(Card card) {
        return this.attackerHolder != null && this.attackerHolder.has(card);
    }

    public boolean targetHas(Card card) {
        return this.targetHolder != null && this.targetHolder.has(card);
    }
}
