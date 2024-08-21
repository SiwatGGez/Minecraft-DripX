package net.fryc.imbleeding.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fryc.imbleeding.ImBleeding;
import net.fryc.imbleeding.network.payloads.CreateBloodParticlePayload;
import net.fryc.imbleeding.network.payloads.SynchronizeConfigPayload;
import net.fryc.imbleeding.network.s2c.CreateBloodParticleS2CPacket;
import net.fryc.imbleeding.network.s2c.SynchronizeConfigS2CPacket;
import net.minecraft.util.Identifier;

public class ModPackets {
    public static final Identifier CREATE_BLOOD_PARTICLE = Identifier.of(ImBleeding.MOD_ID, "create_blood_particle");
    public static final Identifier SYNCHRONIZE_CONFIG = Identifier.of(ImBleeding.MOD_ID, "synchronize_config");


    public static void registerPayloads(){
        PayloadTypeRegistry.playS2C().register(CreateBloodParticlePayload.ID, CreateBloodParticlePayload.CODEC);
        PayloadTypeRegistry.playS2C().register(SynchronizeConfigPayload.ID, SynchronizeConfigPayload.CODEC);
    }
    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(CreateBloodParticlePayload.ID, CreateBloodParticleS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SynchronizeConfigPayload.ID, SynchronizeConfigS2CPacket::receive);
    }
}
