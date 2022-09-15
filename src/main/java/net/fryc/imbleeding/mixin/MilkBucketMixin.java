package net.fryc.imbleeding.mixin;

import net.fryc.imbleeding.effects.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MilkBucketItem.class)
abstract class MilkBucketMixin extends Item {
    public MilkBucketMixin(Settings settings) {
        super(settings);
    }

    //Prevents milk from removing bleed and bleedout
    boolean bl = false, bli = false;
    int dur, duri;
    int ampl, ampli;

    //Gets bleed and bleedout durations and amplifiers
    @Inject(method = "finishUsing(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/item/ItemStack;", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z"))
    private void getBleed(ItemStack stack, World world, LivingEntity user , CallbackInfoReturnable<Object> info) {
            if(user.hasStatusEffect(ModEffects.BLEED_EFFECT)){
                dur = user.getActiveStatusEffects().get(ModEffects.BLEED_EFFECT).getDuration();
                ampl = user.getActiveStatusEffects().get(ModEffects.BLEED_EFFECT).getAmplifier();
                bl = true;
            }
            if(user.hasStatusEffect(ModEffects.BLEEDOUT)){
                duri = user.getActiveStatusEffects().get(ModEffects.BLEEDOUT).getDuration();
                ampli = user.getActiveStatusEffects().get(ModEffects.BLEEDOUT).getAmplifier();
                bli = true;
            }
    }


    //Gives player bleed and bleedout
    @Inject(method = "finishUsing(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/item/ItemStack;", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z", shift = At.Shift.AFTER))
    private void applyBleed(ItemStack stack, World world, LivingEntity user , CallbackInfoReturnable<Object> info) {
            if(bl){
                user.addStatusEffect(new StatusEffectInstance(ModEffects.BLEED_EFFECT, dur, ampl));
                bl = false;
            }
            if(bli){
                user.addStatusEffect(new StatusEffectInstance(ModEffects.BLEEDOUT, duri, ampli));
                bli = false;
            }
    }



}
