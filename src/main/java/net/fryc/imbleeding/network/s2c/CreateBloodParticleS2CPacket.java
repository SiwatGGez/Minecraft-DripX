package net.fryc.imbleeding.network.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fryc.imbleeding.ImBleeding;
import net.fryc.imbleeding.effects.particles.ModParticles;
import net.fryc.imbleeding.network.payloads.CreateBloodParticlePayload;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.world.World;

public class CreateBloodParticleS2CPacket {

    public static void receive(CreateBloodParticlePayload payload, ClientPlayNetworking.Context context){
        ClientPlayerEntity player = context.player();
        if(player != null){
            if(ImBleeding.config.enableBloodParticlesClientSided){
                World world = player.getWorld();
                double x = payload.x();
                double y = payload.y();
                double z = payload.z();
                double vec3d = payload.vec3d();
                world.addParticle(ModParticles.BLOOD_PARTICLE, x, y, z, 0, vec3d, 0);
            }
        }
    }
}
