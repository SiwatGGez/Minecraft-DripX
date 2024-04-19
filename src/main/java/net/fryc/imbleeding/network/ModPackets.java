package net.fryc.imbleeding.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fryc.imbleeding.ImBleeding;
import net.fryc.imbleeding.network.s2c.CreateBloodParticleS2CPacket;
import net.fryc.imbleeding.network.s2c.SynchronizeConfigS2CPacket;
import net.minecraft.util.Identifier;

public class ModPackets {
    public static final Identifier CREATE_BLOOD_PARTICLE = new Identifier(ImBleeding.MOD_ID, "create_blood_particle");
    public static final Identifier SYNCHRONIZE_CONFIG = new Identifier(ImBleeding.MOD_ID, "synchronize_config");


    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(CREATE_BLOOD_PARTICLE, CreateBloodParticleS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SYNCHRONIZE_CONFIG, SynchronizeConfigS2CPacket::receive);
    }
}
