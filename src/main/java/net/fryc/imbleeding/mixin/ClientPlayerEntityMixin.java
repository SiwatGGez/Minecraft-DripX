package net.fryc.imbleeding.mixin;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fryc.imbleeding.effects.ModEffects;
import net.fryc.imbleeding.network.ModPackets;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {


    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "tick()V", at = @At("TAIL"))
    private void spawnBloodParticles(CallbackInfo info) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if(dys.hasStatusEffect(ModEffects.BLEED_EFFECT)){
            if(dys.getRandom().nextInt(100) < 5 + dys.getActiveStatusEffects().get(ModEffects.BLEED_EFFECT).getAmplifier()*3){
                Vec3d vec3d = dys.getVelocity();
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeChunkPos(dys.getChunkPos());
                buf.writeDouble(dys.getX() + (dys.getRandom().nextFloat()/2) - 0.25f);
                buf.writeDouble(dys.getY() + dys.getRandom().nextFloat() + 0.1f);
                buf.writeDouble(dys.getZ() + (dys.getRandom().nextFloat()/2) - 0.25f);
                buf.writeDouble(vec3d.getY()-0.05);
                ClientPlayNetworking.send(ModPackets.INFORM_ABOUT_BLOOD_PARTICLE, buf);
            }
        }
    }
}
