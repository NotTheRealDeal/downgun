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
import net.ntrdeal.downgun.misc.CardHolder;
import net.ntrdeal.downgun.registry.ModRegistryKeys;

public class CardCommand {

    public static final SuggestionProvider<ServerCommandSource> SUGGESTION_PROVIDER = (context, builder) -> {
        PlayerEntity player = EntityArgumentType.getPlayer(context, "player");
        return CommandSource.suggestIdentifiers(((CardHolder)player).ntrdeal$getCardKeys().stream().map(RegistryKey::getValue), builder);
    };

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
        if (player instanceof CardHolder holder && holder.ntrdeal$AddCard(card, level)) {
            return 1;
        } else {
            return 0;
        }
    }

    public static int removeCard(PlayerEntity player, Card card) {
        if (player instanceof CardHolder holder && holder.ntrdeal$removeCard(card)) {
            return 1;
        } else {
            return 0;
        }
    }

    public static int clearCards(PlayerEntity player) {
        if (player instanceof CardHolder holder && holder.ntrdeal$resetCards()) {
            return 1;
        } else {
            return 0;
        }
    }
}
