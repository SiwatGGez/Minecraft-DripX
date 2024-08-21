package net.fryc.imbleeding.network.payloads;

import net.fryc.imbleeding.network.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record CreateBloodParticlePayload(double x, double y, double z, double vec3d) implements CustomPayload {

    public static final Id<CreateBloodParticlePayload> ID = new Id<>(ModPackets.CREATE_BLOOD_PARTICLE);
    public static final PacketCodec<RegistryByteBuf, CreateBloodParticlePayload> CODEC = PacketCodec.tuple(
            PacketCodecs.DOUBLE, CreateBloodParticlePayload::x,
            PacketCodecs.DOUBLE, CreateBloodParticlePayload::y,
            PacketCodecs.DOUBLE, CreateBloodParticlePayload::z,
            PacketCodecs.DOUBLE, CreateBloodParticlePayload::vec3d,
            CreateBloodParticlePayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}