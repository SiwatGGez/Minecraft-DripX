package net.fryc.imbleeding.network.s2c;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fryc.imbleeding.util.ConfigHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class SynchronizeConfigS2CPacket {

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        ConfigHelper.enableCombatRollCompatibility = buf.readBoolean();
    }
}
