package net.fryc.imbleeding.mixin;

import net.fryc.imbleeding.effects.ModEffects;
import net.fryc.imbleeding.util.BleedingHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
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

    @Inject(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At("TAIL"))
    private void applyBrokenEffect(DamageSource source, float amount, CallbackInfoReturnable<Boolean> ret){
        LivingEntity dys = ((LivingEntity)(Object)this);
        float damage = BleedingHelper.reduceFallDamageWithFeatherFalling(amount, EnchantmentHelper.getEquipmentLevel(Enchantments.FEATHER_FALLING, dys));
        if(BleedingHelper.shouldApplyBrokenEffect(source, damage, dys)){
            BleedingHelper.applyBrokenEffect(dys, damage);
        }
    }
}
