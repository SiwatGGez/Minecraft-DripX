package net.fryc.imbleeding.effects.particles;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fryc.imbleeding.ImBleeding;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {

    public static DefaultParticleType BLOOD_PARTICLE;
    private static final DefaultParticleType blood_particle = FabricParticleTypes.simple();


    public static void registerModParticles(){
        if(BLOOD_PARTICLE == null){
            BLOOD_PARTICLE = (DefaultParticleType) registerParticleType(new Identifier(ImBleeding.MOD_ID, "blood"), blood_particle);
        }
    }

    private static ParticleType<? extends ParticleEffect> registerParticleType(Identifier identifier, ParticleType<? extends ParticleEffect> particleType){
        return Registry.register(Registries.PARTICLE_TYPE, identifier, particleType);
    }
}
