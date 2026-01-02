package net.ntrdeal.downgun.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.CommandSource;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.RegistryEntryReferenceArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.ntrdeal.downgun.card.Card;
import net.ntrdeal.downgun.component.ModComponents;
import net.ntrdeal.downgun.registry.ModRegistryKeys;

public class CardCommand {
    public static final SuggestionProvider<ServerCommandSource> SUGGESTION_PROVIDER = (context, builder) -> CommandSource.suggestIdentifiers(ModComponents.CARD_HOLDER.get(EntityArgumentType.getPlayer(context, "player")).getCardKeys().stream().map(RegistryKey::getValue), builder);

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access) {
        LiteralCommandNode<ServerCommandSource> cards = CommandManager.literal("cards").build();

        ArgumentCommandNode<ServerCommandSource, EntitySelector> player = CommandManager.argument("player", EntityArgumentType.player())
                .requires(source -> source.hasPermissionLevel(2)).then(
                        CommandManager.literal("put")
                                .then(CommandManager.argument("card", RegistryEntryReferenceArgumentType.registryEntry(access, ModRegistryKeys.CARDS))
                                        .executes(context -> addCard(
                                                EntityArgumentType.getPlayer(context, "player"),
                                                RegistryEntryReferenceArgumentType.getRegistryEntry(context, "card", ModRegistryKeys.CARDS).value(),
                                                1
                                        ))
                                        .then(CommandManager.argument("level", IntegerArgumentType.integer(1)).executes(
                                                context -> addCard(
                                                        EntityArgumentType.getPlayer(context, "player"),
                                                        RegistryEntryReferenceArgumentType.getRegistryEntry(context, "card", ModRegistryKeys.CARDS).value(),
                                                        IntegerArgumentType.getInteger(context, "level")
                                                )
                                        )))
                ).then(
                        CommandManager.literal("remove")
                                .then(CommandManager.argument("card", RegistryEntryReferenceArgumentType.registryEntry(access, ModRegistryKeys.CARDS))
                                        .suggests(SUGGESTION_PROVIDER)
                                        .executes(
                                        context -> removeCard(
                                                EntityArgumentType.getPlayer(context, "player"),
                                                RegistryEntryReferenceArgumentType.getRegistryEntry(context, "card", ModRegistryKeys.CARDS).value()
                                        )
                                ))
                ).then(
                        CommandManager.literal("clear").executes(
                                context -> clearCards(
                                        EntityArgumentType.getPlayer(context, "player")
                                )
                        )
                ).build();

        dispatcher.getRoot().addChild(cards);
        cards.addChild(player);
    }


    public static int addCard(PlayerEntity player, Card card, int level) {
        return ModComponents.CARD_HOLDER.get(player).addCard(card, level) ? 1 : 0;
    }

    public static int removeCard(PlayerEntity player, Card card) {
        return ModComponents.CARD_HOLDER.get(player).removeCard(card) ? 1 : 0;
    }

    public static int clearCards(PlayerEntity player) {
        return ModComponents.CARD_HOLDER.get(player).clearCards() ? 1 : 0;
    }
}
