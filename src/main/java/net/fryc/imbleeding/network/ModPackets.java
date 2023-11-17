package net.fryc.imbleeding.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fryc.imbleeding.ImBleeding;
import net.fryc.imbleeding.network.c2s.InformAboutBloodParticleC2SPacket;
import net.fryc.imbleeding.network.s2c.CreateBloodParticleS2CPacket;
import net.minecraft.util.Identifier;

public class ModPackets {
    public static final Identifier INFORM_ABOUT_BLOOD_PARTICLE = new Identifier(ImBleeding.MOD_ID, "inform_about_blood_particle");
    public static final Identifier CREATE_BLOOD_PARTICLE = new Identifier(ImBleeding.MOD_ID, "create_blood_particle");

    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(INFORM_ABOUT_BLOOD_PARTICLE, InformAboutBloodParticleC2SPacket::receive);
    }

    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(CREATE_BLOOD_PARTICLE, CreateBloodParticleS2CPacket::receive);
    }
}
