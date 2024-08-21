package net.fryc.imbleeding.mixin;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fryc.imbleeding.ImBleeding;
import net.fryc.imbleeding.network.payloads.SynchronizeConfigPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
abstract class ServerWorldMixin {

    @Inject(method = "onPlayerConnected(Lnet/minecraft/server/network/ServerPlayerEntity;)V", at = @At("TAIL"))
    private void sendConfigToClient(ServerPlayerEntity player, CallbackInfo info) {
        ServerPlayNetworking.send(player, new SynchronizeConfigPayload(ImBleeding.config.enableCombatRollCompatibility));
    }
}
