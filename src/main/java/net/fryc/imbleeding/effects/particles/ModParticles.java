package net.fryc.imbleeding.effects.particles;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fryc.imbleeding.ImBleeding;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {

    public static SimpleParticleType BLOOD_PARTICLE;
    public static SimpleParticleType BLOOD_PARTICLE_LAND;
    private static final SimpleParticleType blood_particle = FabricParticleTypes.simple();
    private static final SimpleParticleType blood_particle_land = FabricParticleTypes.simple();


    public static void registerModParticles(){
        if(BLOOD_PARTICLE == null){
            BLOOD_PARTICLE = (SimpleParticleType) registerParticleType(Identifier.of(ImBleeding.MOD_ID, "blood"), blood_particle);
            BLOOD_PARTICLE_LAND = (SimpleParticleType) registerParticleType(Identifier.of(ImBleeding.MOD_ID, "blood_land"), blood_particle_land);
        }
    }

    private static ParticleType<? extends ParticleEffect> registerParticleType(Identifier identifier, ParticleType<? extends ParticleEffect> particleType){
        return Registry.register(Registries.PARTICLE_TYPE, identifier, particleType);
    }
}
