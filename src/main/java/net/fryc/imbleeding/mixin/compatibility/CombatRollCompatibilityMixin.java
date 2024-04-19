package net.fryc.imbleeding.mixin.compatibility;

import net.fryc.imbleeding.effects.ModEffects;
import net.fryc.imbleeding.util.ConfigHelper;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "net.combatroll.client.RollManager")
abstract class CombatRollCompatibilityMixin {


    @Inject(method = "isRollAvailable(Lnet/minecraft/entity/player/PlayerEntity;)Z", at = @At("HEAD"), cancellable = true)
    private void disableRollingWhenBroken(PlayerEntity player, CallbackInfoReturnable<Boolean> ret) {
        if(ConfigHelper.isCombatRollCompatibilityEnabled(player.getWorld())){
            if(player.hasStatusEffect(ModEffects.BROKEN)){
                ret.setReturnValue(false);
            }
        }
    }


}
