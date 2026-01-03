package net.ntrdeal.downgun.component;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.ntrdeal.downgun.card.Card;
import net.ntrdeal.downgun.component.misc.CardMap;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class CardHolderComponent implements AutoSyncedComponent, CommonTickingComponent {
    public final PlayerEntity player;
    public CardMap cards = new CardMap();

    public CardHolderComponent(PlayerEntity player) {
        this.player = player;
    }

    public boolean addCard(Card card, int level) {
        boolean added = this.cards.addCard(this.player, card, level);
        ModComponents.CARD_HOLDER.sync(this.player);
        return added;
    }

    public boolean removeCard(Card card) {
        boolean removed = this.cards.removeCard(this.player, card);
        ModComponents.CARD_HOLDER.sync(this.player);
        return removed;
    }

    public boolean clearCards() {
        boolean cleared = this.cards.resetCards(this.player);
        ModComponents.CARD_HOLDER.sync(this.player);
        return cleared;
    }

    public void forEach(BiConsumer<Card, Integer> consumer) {
        this.cards.forEach(consumer);
    }

    public Set<RegistryKey<Card>> getCardKeys() {
        return this.cards.getCardKeys();
    }

    public List<Map.Entry<Card, Integer>> getLayeredCards() {
        return this.cards.layeredCards();
    }

    public boolean has(Card card) {
        return this.cards.containsKey(card);
    }

    @Override
    public void tick() {
        this.getLayeredCards().forEach(entry -> entry.getKey().tick(this, entry.getValue()));
    }

    @Override
    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        CardMap.CODEC.parse(lookup.getOps(NbtOps.INSTANCE), nbt.get("cards")).result().ifPresent(cardMap -> this.cards = cardMap);
    }

    @Override
    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        CardMap.CODEC.encodeStart(lookup.getOps(NbtOps.INSTANCE), this.cards).result().ifPresent(element -> nbt.put("cards", element));
    }
}