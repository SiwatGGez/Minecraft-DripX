package net.fryc.imbleeding.mixin;

import net.fryc.imbleeding.effects.ModEffects;
import net.fryc.imbleeding.util.ClearEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;
import java.util.Map;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin extends Entity implements ClearEffects {

    @Shadow
    private @Final Map<StatusEffect, StatusEffectInstance> activeStatusEffects;


    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    protected void onStatusEffectRemoved(StatusEffectInstance effect) {
    }

    //removes all status effects except bleeding and bleedout
    public boolean clearStatusEffectsExceptBleeding() {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if (dys.world.isClient) {
            return false;
        } else {
            Iterator<StatusEffectInstance> iterator = this.activeStatusEffects.values().iterator();

            boolean bl;
            StatusEffectInstance instance;
            for(bl = false; iterator.hasNext(); bl = true) {
                instance = iterator.next();
                if(instance.getEffectType() != ModEffects.BLEED_EFFECT && instance.getEffectType() != ModEffects.BLEEDOUT){
                    this.onStatusEffectRemoved(instance);
                    iterator.remove();
                }
            }

            return bl;
        }
    }


    //undead enemies cant get bleeding
    @Inject(method = "canHaveStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;)Z", at = @At("HEAD"), cancellable = true)
    private void injected(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> ret) {
        LivingEntity dys = ((LivingEntity)(Object)this);
        if(dys.getGroup() == EntityGroup.UNDEAD){
            if(effect.getEffectType() == ModEffects.BLEED_EFFECT || effect.getEffectType() == ModEffects.BLEEDOUT){
                ret.setReturnValue(false);
            }
        }
    }
}
