package net.fryc.imbleeding.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fryc.imbleeding.ImBleeding;
import net.fryc.imbleeding.network.s2c.CreateBloodParticleS2CPacket;
import net.minecraft.util.Identifier;

public class ModPackets {
    public static final Identifier CREATE_BLOOD_PARTICLE = new Identifier(ImBleeding.MOD_ID, "create_blood_particle");


    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(CREATE_BLOOD_PARTICLE, CreateBloodParticleS2CPacket::receive);
    }
}
