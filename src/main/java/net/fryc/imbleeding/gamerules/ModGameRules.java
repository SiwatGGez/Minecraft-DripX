package net.fryc.imbleeding.gamerules;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class ModGameRules {

    public static GameRules.Key<GameRules.BooleanRule> BLEEDOUT_KILLS;
    public static GameRules.Key<GameRules.BooleanRule> BLEEDING_STOPS_FOOD_HEALING;
    public static GameRules.Key<GameRules.BooleanRule> ENABLE_MELEE_EFFECT_UPGRADING;
    public static GameRules.Key<GameRules.BooleanRule> ENABLE_ARROW_EFFECT_UPGRADING;
    public static GameRules.Key<GameRules.BooleanRule> ENABLE_DARKNESS_AT_LOW_HP;
    public static GameRules.Key<GameRules.IntRule> MELEE_BLEED_LENGTH;
    public static GameRules.Key<GameRules.IntRule> ARROW_BLEED_LENGTH;
    public static GameRules.Key<GameRules.IntRule> HEALTH_LOSS_LENGTH;

    public static void registerGameRules(){
        BLEEDOUT_KILLS = GameRuleRegistry.register("bleedoutKills", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(true));
        BLEEDING_STOPS_FOOD_HEALING = GameRuleRegistry.register("bleedingStopsFoodHealing", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(true));
        ENABLE_MELEE_EFFECT_UPGRADING = GameRuleRegistry.register("enableMeleeEffectUpgrading", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(true));
        ENABLE_ARROW_EFFECT_UPGRADING = GameRuleRegistry.register("enableArrowEffectUpgrading", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(false));
        ENABLE_DARKNESS_AT_LOW_HP = GameRuleRegistry.register("enableDarknessAtLowHp", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(true));

        MELEE_BLEED_LENGTH = GameRuleRegistry.register("meleeBleedLength", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(11, 0, 200));
        ARROW_BLEED_LENGTH = GameRuleRegistry.register("arrowBleedLength", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(7, 0, 200));
        HEALTH_LOSS_LENGTH = GameRuleRegistry.register("healthLossLength", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(280, 0, 4000));
    }
}
