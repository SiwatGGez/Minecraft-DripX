package net.fryc.imbleeding;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fryc.imbleeding.effects.particles.BloodParticle;
import net.fryc.imbleeding.effects.particles.BloodParticleLand;
import net.fryc.imbleeding.effects.particles.ModParticles;
import net.fryc.imbleeding.items.custom.BalmItem;
import net.fryc.imbleeding.network.ModPackets;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class ImBleedingClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelPredicateProviderRegistry.register(new Identifier("balm"), (stack, world, entity, seed) -> {
            return entity != null && entity.isUsingItem() && entity.getActiveItem().getItem() instanceof BalmItem ? 1.0F : 0.0F;
        });

        ParticleFactoryRegistry.getInstance().register(ModParticles.BLOOD_PARTICLE, BloodParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.BLOOD_PARTICLE_LAND, BloodParticleLand.Factory::new);
        ModPackets.registerS2CPackets();
    }
}
