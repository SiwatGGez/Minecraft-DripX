package net.fryc.imbleeding.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "imbleeding")
public class ImbleedingConfig implements ConfigData {

    //booleans
    @ConfigEntry.Category("bleeding")
    public boolean bleedoutKills = true;

    @ConfigEntry.Category("bleeding")
    public boolean bleedingStopsFoodHealing = true;

    @ConfigEntry.Category("bleeding")
    public boolean fireDamageLowersBleedingDuration = true;

    @ConfigEntry.Category("bleeding")
    @ConfigEntry.Gui.Tooltip
    public boolean enableMeleeEffectUpgrading = true;

    @ConfigEntry.Category("bleeding")
    @ConfigEntry.Gui.Tooltip
    public boolean enableArrowEffectUpgrading = false;

    //length
    @ConfigEntry.Category("bleeding_length")
    public int meleeBleedLength = 200;

    @ConfigEntry.Category("bleeding_length")
    public int healthLossLength = 1450;


    @ConfigEntry.Category("bleeding_length")
    public int arrowBleedLength = 115;


    //visual
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public boolean enableDarknessAtLowHp = true;
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public boolean enableBlindnessAtLowHp = true;


}
