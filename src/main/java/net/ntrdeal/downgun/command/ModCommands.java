package net.ntrdeal.downgun.command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, access, environment) -> {
            CardCommand.register(dispatcher, access);
        });
    }
}
