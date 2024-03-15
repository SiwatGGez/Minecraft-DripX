package net.fryc.imbleeding.util;

import net.fryc.imbleeding.ImBleeding;
import net.fryc.imbleeding.effects.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;

public class BleedingHelper {

    public static void applyDarknessToPlayer(PlayerEntity player){
        if(player.getHealth() < 6 && ImBleeding.config.enableDarknessAtLowHp){
            if(player.hasStatusEffect(StatusEffects.DARKNESS)){
                if(player.getActiveStatusEffects().get(StatusEffects.DARKNESS).getDuration() < 34){
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 33 , 0, false, false, false));
                }
            }
            else player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 33 , 0, false, false, false));
        }
    }

    public static void applyBlindnessToPlayer(PlayerEntity player){
        if(player.getHealth()<=1 && ImBleeding.config.enableBlindnessAtLowHp){
            if(player.hasStatusEffect(StatusEffects.BLINDNESS)){
                if(player.getActiveStatusEffects().get(StatusEffects.BLINDNESS).getDuration() < 25) player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 60 , 0, false, false, false));
            }
            else player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 60 , 0, false, false, false));
        }
    }

    public static void applyBleedingOrHealthLoss(PlayerEntity player, int duration, boolean healthLoss, DamageSource source, float amount){
        StatusEffect effect;
        if(!healthLoss) effect = ModEffects.BLEED_EFFECT;
        else effect = ModEffects.HEALTH_LOSS;

        if(!player.hasStatusEffect(effect)){
            player.addStatusEffect(new StatusEffectInstance(effect, duration, 0, false, false, true));
        }
        else{
            int amp = player.getActiveStatusEffects().get(effect).getAmplifier();
            int bleedingUpgradeChance;
            if(amp == 0){
                bleedingUpgradeChance = (int)(ImBleeding.config.baseChanceToUpgradeBleedingOrHealthLoss * (amount + 1));
            }
            else{
                bleedingUpgradeChance = (int)((1+(ImBleeding.config.baseChanceToUpgradeBleedingOrHealthLoss/10)) * (amount + 1));
            }
            if(!player.getWorld().isClient() && amp < 3 && checkIfBleedingCanBeUpgraded(source)){
                if(player.getRandom().nextInt(100) >= 100 - bleedingUpgradeChance){
                    amp++;
                }
            }
            if(player.getActiveStatusEffects().get(effect).getDuration() > duration){
                duration -= duration * 0.75;
            }
            else{
                duration -= player.getActiveStatusEffects().get(effect).getDuration() * 0.75;
            }
            duration += player.getActiveStatusEffects().get(effect).getDuration();

            player.addStatusEffect(new StatusEffectInstance(effect, duration, amp, false, false, true));
        }
    }

    public static void reduceBleedingWithFire(PlayerEntity player){
        if(player.hasStatusEffect(ModEffects.BLEED_EFFECT)){
            int amp = player.getActiveStatusEffects().get(ModEffects.BLEED_EFFECT).getAmplifier();
            int dur = player.getActiveStatusEffects().get(ModEffects.BLEED_EFFECT).getDuration();
            if(amp == 0) dur -= 280;
            else if(amp == 1) dur -= 120;
            else dur -= 50;
            if(!player.getWorld().isClient() && amp > 0){
                if(player.getRandom().nextInt(100) >= 100 - ImBleeding.config.chanceToLowerBleedingAmplifierWithFire){
                    amp--;
                }
            }
            player.removeStatusEffect(player.getActiveStatusEffects().get(ModEffects.BLEED_EFFECT).getEffectType());
            if(dur > 0){
                player.addStatusEffect(new StatusEffectInstance(ModEffects.BLEED_EFFECT, dur, amp, false, false, true));
            }
        }
    }

    private static boolean checkIfBleedingCanBeUpgraded(DamageSource source){
        if(source.isIn(DamageTypeTags.IS_PROJECTILE)) return ImBleeding.config.enableArrowEffectUpgrading;
        return ImBleeding.config.enableMeleeEffectUpgrading;
    }

    public static boolean shouldStopFoodHealing(PlayerEntity player){
        return player.getHealth() > 0.0F && player.getHealth() < player.getMaxHealth() && !player.hasStatusEffect(ModEffects.BLEED_EFFECT);
    }

    public static void applyBrokenEffect(LivingEntity entity, float damage){
        int duration;
        if(entity.hasStatusEffect(ModEffects.BROKEN)){
            duration = entity.getActiveStatusEffects().get(ModEffects.BROKEN).getDuration() + (int)(ImBleeding.config.brokenLengthPerHealthPointLost*damage);
        }
        else {
            duration = ImBleeding.config.baseBrokenLength + (int)(ImBleeding.config.brokenLengthPerHealthPointLost*(damage-ImBleeding.config.minFallDamageTakenToGetBroken));
        }
        entity.addStatusEffect(new StatusEffectInstance(ModEffects.BROKEN, duration, 0, false, false, true));
    }

    public static boolean shouldApplyBrokenEffect(DamageSource source, float damage, LivingEntity entity){
        return source.isIn(DamageTypeTags.IS_FALL) && (damage > ImBleeding.config.minFallDamageTakenToGetBroken || entity.hasStatusEffect(ModEffects.BROKEN));
    }

}
