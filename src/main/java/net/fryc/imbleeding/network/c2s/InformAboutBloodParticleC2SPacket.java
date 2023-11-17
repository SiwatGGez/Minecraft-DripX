package net.fryc.imbleeding.network.c2s;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fryc.imbleeding.effects.ModEffects;
import net.fryc.imbleeding.network.ModPackets;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.ChunkPos;


public class InformAboutBloodParticleC2SPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        if(player.hasStatusEffect(ModEffects.BLEED_EFFECT)){
            ChunkPos chunk = buf.readChunkPos();
            for (ServerPlayerEntity pl : PlayerLookup.tracking(player.getServerWorld(), chunk)) {
                ServerPlayNetworking.send(pl, ModPackets.CREATE_BLOOD_PARTICLE, buf);
            }
        }
    }
}
