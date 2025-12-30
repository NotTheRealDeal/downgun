package net.ntrdeal.downgun.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.World;
import net.ntrdeal.downgun.card.Card;
import net.ntrdeal.downgun.misc.CardHolder;
import net.ntrdeal.downgun.network.ModTrackedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.TreeMap;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements CardHolder {
    @Unique private static final TrackedData<Map<Card, Integer>> CARDS = DataTracker.registerData(PlayerEntityMixin.class, ModTrackedData.TRACK_CARDS);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void ntrdeal$tickCards(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        this.ntrdeal$getCards().forEach((card, level) -> card.tick(player, level));
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void ntrdeal$addTrackedCards(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(CARDS, new TreeMap<>(Card.SORTED));
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void ntrdeal$readCards(NbtCompound nbt, CallbackInfo ci) {
        Card.CARD_MAP_CODEC.parse(this.getRegistryManager().getOps(NbtOps.INSTANCE), nbt.get("cards")).result().ifPresent(this::ntrdeal$setCards);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void ntrdeal$writeCards(NbtCompound nbt, CallbackInfo ci) {
        Card.CARD_MAP_CODEC.encodeStart(this.getRegistryManager().getOps(NbtOps.INSTANCE), this.ntrdeal$getCards()).result().ifPresent(cards -> nbt.put("cards", cards));
    }

    @Override
    public void ntrdeal$setCards(Map<Card, Integer> cards) {
        this.dataTracker.set(CARDS, cards);
    }

    @Override
    public Map<Card, Integer> ntrdeal$getCards() {
        return this.dataTracker.get(CARDS);
    }
}
