package net.fryc.imbleeding.util;

import net.fryc.imbleeding.ImBleeding;
import net.minecraft.world.World;

public class ConfigHelper {

    public static boolean enableCombatRollCompatibility = ImBleeding.config.enableCombatRollCompatibility;

    public static boolean isCombatRollCompatibilityEnabled(World world){
        if(world.isClient()){
            return enableCombatRollCompatibility;
        }
        return ImBleeding.config.enableCombatRollCompatibility;
    }
}
