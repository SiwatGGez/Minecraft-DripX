package net.fryc.imbleeding.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "imbleeding")
public class ImbleedingConfig implements ConfigData {


    @ConfigEntry.Category("bleeding")
    public float bleedingDamage = 0.5F;

    @ConfigEntry.Category("bleeding")
    @ConfigEntry.Gui.Tooltip
    public int bleedingDamageFrequency = 120;
    @ConfigEntry.Category("bleeding")
    public boolean bleedoutKills = true;

    @ConfigEntry.Category("bleeding")
    public boolean bleedingStopsFoodHealing = true;

    @ConfigEntry.Category("bleeding")
    public boolean fireDamageLowersBleedingDuration = true;

    @ConfigEntry.Category("bleeding")
    public int chanceToLowerBleedingAmplifierWithFire = 28;

    @ConfigEntry.Category("bleeding")
    @ConfigEntry.Gui.Tooltip
    public boolean enableMeleeEffectUpgrading = true;

    @ConfigEntry.Category("bleeding")
    @ConfigEntry.Gui.Tooltip
    public boolean enableArrowEffectUpgrading = false;

    @ConfigEntry.Category("bleeding")
    @ConfigEntry.Gui.Tooltip
    public int baseChanceToUpgradeBleedingOrHealthLoss = 7;

    @ConfigEntry.Category("bleeding")
    public float minFallDamageTakenToGetBroken = 9F;


    //length
    @ConfigEntry.Category("bleeding_length")
    @ConfigEntry.Gui.Tooltip
    public int meleeBleedLength = 200;

    @ConfigEntry.Category("bleeding_length")
    @ConfigEntry.Gui.Tooltip
    public int healthLossLength = 1450;

    @ConfigEntry.Category("bleeding_length")
    @ConfigEntry.Gui.Tooltip
    public int arrowBleedLength = 100;

    @ConfigEntry.Category("bleeding_length")
    @ConfigEntry.Gui.Tooltip
    public int bleedoutLength = 1200;

    @ConfigEntry.Category("bleeding_length")
    public float armorBleedingProtection = 3;
    @ConfigEntry.Category("bleeding_length")
    public float toughnessBleedingProtection = 4;

    @ConfigEntry.Category("bleeding_length")
    @ConfigEntry.Gui.Tooltip
    public int baseBrokenLength = 600;

    @ConfigEntry.Category("bleeding_length")
    @ConfigEntry.Gui.Tooltip
    public int brokenLengthPerHealthPointLost = 240;


    //visual
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public boolean enableDarknessAtLowHp = true;
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public boolean enableBlindnessAtLowHp = true;
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public boolean enableBloodParticlesClientSided = true;

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("compatibility")
    public boolean enableCombatRollCompatibility = true;

}
