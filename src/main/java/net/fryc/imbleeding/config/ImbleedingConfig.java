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
    @ConfigEntry.BoundedDiscrete(max = 200)
    public int meleeBleedLength = 11;

    @ConfigEntry.Category("bleeding_length")
    @ConfigEntry.BoundedDiscrete(max = 4000)
    public int healthLossLength = 280;


    @ConfigEntry.Category("bleeding_length")
    @ConfigEntry.BoundedDiscrete(max = 200)
    public int arrowBleedLength = 7;


    //visual
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public boolean enableDarknessAtLowHp = true;
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public boolean enableBlindnessAtLowHp = true;


}
