package net.fryc.imbleeding.mixin;

import net.fryc.imbleeding.ImBleeding;
import net.fryc.imbleeding.effects.ModEffects;
import net.fryc.imbleeding.tags.ModDamageTypeTags;
import net.fryc.imbleeding.tags.ModEntityTypeTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    Random random = Random.create();
    //Causes player to bleed after taking damage and gives darkness at low hp
    @Inject(method = "applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V", at = @At("TAIL"))
    public void applyDamageEffects(DamageSource source, float amount, CallbackInfo ci) {
        PlayerEntity player = ((PlayerEntity) (Object) this);

        //applying darkness
        if(player.getHealth() < 6 && ImBleeding.config.enableDarknessAtLowHp){
            if(player.hasStatusEffect(StatusEffects.DARKNESS)){
                if(player.getActiveStatusEffects().get(StatusEffects.DARKNESS).getDuration() < 34){
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 33 , 0, false, false, false));
                    player.getActiveStatusEffects().get(StatusEffects.DARKNESS).applyUpdateEffect(player);
                }
            }
            else player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 33 , 0, false, false, false));
        }

        float toughness = (int) (player.getAttributes().getValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS));
        int armor = (int) (player.getAttributes().getValue(EntityAttributes.GENERIC_ARMOR));

        //calculating bleeding duration
        Entity attacker = source.getAttacker();
        int duration = 0;
        boolean healthLoss = false;
        if(amount >= 1 && source.isIn(ModDamageTypeTags.DAMAGE_APPLY_BLEED)){
            float reduction = ((ImBleeding.config.armorBleedingProtection * armor) + (ImBleeding.config.toughnessBleedingProtection * toughness))/100; // bleeding reduction in %
            if(attacker != null && !source.isIn(DamageTypeTags.IS_PROJECTILE)){
                if(attacker instanceof SpiderEntity){
                    duration = (int) (ImBleeding.config.healthLossLength * amount);
                    healthLoss = true;
                }
                else if(!attacker.getType().isIn(ModEntityTypeTags.NO_BLEEDING_APPLY_MOBS)){
                    duration = (int) (ImBleeding.config.meleeBleedLength * amount);
                    if(attacker instanceof LivingEntity livingEntity){
                        if(livingEntity.getMainHandStack().isDamageable()){
                            duration += duration * 0.17;
                        }
                    }
                }
            }
            if(source.getSource() != null){
                if(source.getSource().getType().isIn(ModEntityTypeTags.BLEEDING_PROJECTILES)){
                    duration = (int) (ImBleeding.config.arrowBleedLength * amount);
                }
            }

            duration -= duration * reduction;
        }


        //applying bleeding or health loss
        if(duration > 19){
            StatusEffect effect;
            if(!healthLoss) effect = ModEffects.BLEED_EFFECT;
            else effect = ModEffects.HEALTH_LOSS;

            if(!player.hasStatusEffect(effect)){
                player.addStatusEffect(new StatusEffectInstance(effect, duration, 0, false, healthLoss, true));
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
                if(!world.isClient() && amp < 3 && checkIfBleedingCanBeUpgraded(source)){
                    if(random.nextInt(100) >= 100 - bleedingUpgradeChance){
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

                player.addStatusEffect(new StatusEffectInstance(effect, duration, amp, false, healthLoss, true));
            }
        }


        //reducing bleeding duration (fire damage)
        if(source.isIn(DamageTypeTags.IS_FIRE) && ImBleeding.config.fireDamageLowersBleedingDuration){
            if(player.hasStatusEffect(ModEffects.BLEED_EFFECT)){
                int amp = player.getActiveStatusEffects().get(ModEffects.BLEED_EFFECT).getAmplifier();
                int dur = player.getActiveStatusEffects().get(ModEffects.BLEED_EFFECT).getDuration();
                if(amp == 0) dur -= 280;
                else if(amp == 1) dur -= 120;
                else dur -= 50;
                if(!world.isClient && amp > 0){
                    if(random.nextInt(100) >= 100 - ImBleeding.config.chanceToLowerBleedingAmplifierWithFire){
                        amp--;
                    }
                }
                player.removeStatusEffect(player.getActiveStatusEffects().get(ModEffects.BLEED_EFFECT).getEffectType());
                if(dur > 0){
                    player.addStatusEffect(new StatusEffectInstance(ModEffects.BLEED_EFFECT, dur, amp, false, false, true));
                }
            }
        }

    }


    //Stops food healing while bleeding
    @Inject(method = "canFoodHeal()Z", at = @At("RETURN"), cancellable = true)
    public void noHealing(CallbackInfoReturnable<Boolean> ret) {
        PlayerEntity player = ((PlayerEntity) (Object) this);
        if(ImBleeding.config.bleedingStopsFoodHealing){
            ret.setReturnValue(player.getHealth() > 0.0F && player.getHealth() < player.getMaxHealth() && !player.hasStatusEffect(ModEffects.BLEED_EFFECT));
        }
    }

    //Gives blindness if player has 1 hp or lower
    @Inject(method = "tick()V", at = @At("TAIL"))
    public void setBlindness(CallbackInfo info){
        PlayerEntity player = ((PlayerEntity) (Object) this);
        if(player.getHealth()<=1 && ImBleeding.config.enableBlindnessAtLowHp){
            if(player.hasStatusEffect(StatusEffects.BLINDNESS)){
                if(player.getActiveStatusEffects().get(StatusEffects.BLINDNESS).getDuration() < 25) player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 60 , 0, false, false, false));
            }
            else player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 60 , 0, false, false, false));
        }
    }

    private static boolean checkIfBleedingCanBeUpgraded(DamageSource source){
        if(source.isIn(DamageTypeTags.IS_PROJECTILE)) return ImBleeding.config.enableArrowEffectUpgrading;
        return ImBleeding.config.enableMeleeEffectUpgrading;
    }

}
