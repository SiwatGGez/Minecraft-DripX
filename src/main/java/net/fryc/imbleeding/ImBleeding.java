package net.fryc.imbleeding;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fryc.imbleeding.config.ImbleedingConfig;
import net.fryc.imbleeding.effects.ModEffects;
import net.fryc.imbleeding.effects.particles.ModParticles;
import net.fryc.imbleeding.items.ModItems;
import net.fryc.imbleeding.recipes.ModRecipeSerializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImBleeding implements ModInitializer {
	public static final String MOD_ID = "imbleeding";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static ImbleedingConfig config;

	@Override
	public void onInitialize() {
		AutoConfig.register(ImbleedingConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(ImbleedingConfig.class).getConfig();

		ModRecipeSerializers.registerModRecipeSerializers();
		ModEffects.registerEffects();
		ModItems.registerModItems();
		ModParticles.registerModParticles();

	}
}
