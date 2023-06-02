package net.fryc.imbleeding.mixin;

import net.fryc.imbleeding.effects.ModEffects;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
abstract class LivingEntityRendererMixin {

    @Inject(method = "isShaking(Lnet/minecraft/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
    private void shakeWhenBleedingOut(LivingEntity entity, CallbackInfoReturnable<Boolean> ret) {
        if(entity.hasStatusEffect(ModEffects.BLEEDOUT)) ret.setReturnValue(true);
    }
}
