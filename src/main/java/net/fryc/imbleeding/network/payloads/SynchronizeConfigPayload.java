package net.fryc.imbleeding.network.payloads;

import net.fryc.imbleeding.network.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record SynchronizeConfigPayload(boolean combatRollCompat) implements CustomPayload {

    public static final Id<SynchronizeConfigPayload> ID = new Id<>(ModPackets.SYNCHRONIZE_CONFIG);
    public static final PacketCodec<RegistryByteBuf, SynchronizeConfigPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, SynchronizeConfigPayload::combatRollCompat,
            SynchronizeConfigPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}