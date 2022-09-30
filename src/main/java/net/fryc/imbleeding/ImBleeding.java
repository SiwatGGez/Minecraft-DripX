package net.fryc.imbleeding;

import net.fabricmc.api.ModInitializer;
import net.fryc.imbleeding.effects.ModEffects;
import net.fryc.imbleeding.gamerules.ModGameRules;
import net.fryc.imbleeding.items.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImBleeding implements ModInitializer {
	public static final String MOD_ID = "imbleeding";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModEffects.registerEffects();
		ModItems.registerModItems();

		ModGameRules.registerGameRules();
	}
}
