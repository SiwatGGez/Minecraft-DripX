package net.fryc.imbleeding.mixin;

import net.fryc.imbleeding.effects.ModEffects;
import net.fryc.imbleeding.tags.ModItemTags;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin extends Entity implements Attackable {


    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }



    //undead enemies cant get bleeding
    @Inject(method = "canHaveStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;)Z", at = @At("HEAD"), cancellable = true)
    private void undeadCantBleed(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> ret) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if(dys.getGroup() == EntityGroup.UNDEAD){
            if(effect.getEffectType() == ModEffects.BLEED_EFFECT || effect.getEffectType() == ModEffects.BLEEDOUT){
                ret.setReturnValue(false);
            }
        }
    }

    @Inject(method = "setSprinting(Z)V", at = @At("HEAD"), cancellable = true)
    private void dontSprintWhenBroken(boolean sprinting, CallbackInfo info) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if(sprinting && dys.hasStatusEffect(ModEffects.BROKEN)){
            info.cancel();
        }
    }

    @Inject(method = "consumeItem()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;clearActiveItem()V"))
    private void removeEffectsAfterUsingItem(CallbackInfo info) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if(!dys.getWorld().isClient()){
            if(dys.getActiveItem().isIn(ModItemTags.ITEMS_REMOVE_BLEEDING)){
                dys.removeStatusEffect(ModEffects.BLEED_EFFECT);
            }
            if(dys.getActiveItem().isIn(ModItemTags.ITEMS_REMOVE_HEALTH_LOSS)){
                dys.removeStatusEffect(ModEffects.HEALTH_LOSS);
            }
            if(dys.getActiveItem().isIn(ModItemTags.ITEMS_REMOVE_BROKEN)){
                dys.removeStatusEffect(ModEffects.BROKEN);
            }
            if(dys.getActiveItem().isIn(ModItemTags.ITEMS_REMOVE_BLEEDOUT)){
                dys.removeStatusEffect(ModEffects.BLEEDOUT);
            }
        }
    }

}
