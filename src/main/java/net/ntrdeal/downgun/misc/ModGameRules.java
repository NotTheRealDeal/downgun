package net.ntrdeal.downgun.misc;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class ModGameRules {

    public static final GameRules.Key<GameRules.IntRule> ROUNDS = GameRuleRegistry.register(
            "rounds",
            GameRules.Category.PLAYER,
            GameRuleFactory.createIntRule(10, 1)
    );

    public static final GameRules.Key<GameRules.IntRule> SELECTION_TIME = GameRuleRegistry.register(
            "selection_time",
            GameRules.Category.PLAYER,
            GameRuleFactory.createIntRule(15, 5)
    );

    public static final GameRules.Key<GameRules.IntRule> ROUND_TIME = GameRuleRegistry.register(
            "round_time",
            GameRules.Category.PLAYER,
            GameRuleFactory.createIntRule(180, 30)
    );

    public static final GameRules.Key<GameRules.IntRule> RESPAWN_TIME = GameRuleRegistry.register(
            "respawn_time",
            GameRules.Category.PLAYER,
            GameRuleFactory.createIntRule(3, 0)
    );

    public static final GameRules.Key<GameRules.IntRule> LIVES = GameRuleRegistry.register(
            "lives",
            GameRules.Category.PLAYER,
            GameRuleFactory.createIntRule(3, 1)
    );

    public static void register() {}
}
