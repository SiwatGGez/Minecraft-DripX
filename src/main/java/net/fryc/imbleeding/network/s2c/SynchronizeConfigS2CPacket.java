package net.fryc.imbleeding.network.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fryc.imbleeding.network.payloads.SynchronizeConfigPayload;
import net.fryc.imbleeding.util.ConfigHelper;

public class SynchronizeConfigS2CPacket {

    public static void receive(SynchronizeConfigPayload payload, ClientPlayNetworking.Context context){
        ConfigHelper.enableCombatRollCompatibility = payload.combatRollCompat();
    }
}
