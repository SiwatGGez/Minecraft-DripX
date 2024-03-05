package net.fryc.imbleeding.mixin;

import net.fryc.imbleeding.ImBleeding;
import net.fryc.imbleeding.tags.ModDamageTypeTags;
import net.fryc.imbleeding.tags.ModEntityTypeTags;
import net.fryc.imbleeding.util.BleedingHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;
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

    //Causes player to bleed after taking damage and gives darkness at low hp
    @Inject(method = "applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V", at = @At("TAIL"))
    public void applyDamageEffects(DamageSource source, float amount, CallbackInfo ci) {
        PlayerEntity player = ((PlayerEntity) (Object) this);

        //applying darkness
        BleedingHelper.applyDarknessToPlayer(player);

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
            BleedingHelper.applyBleedingOrHealthLoss(player, duration, healthLoss, source, amount);
        }

        //reducing bleeding duration (fire damage)
        if(source.isIn(DamageTypeTags.IS_FIRE) && ImBleeding.config.fireDamageLowersBleedingDuration){
            BleedingHelper.reduceBleedingWithFire(player);
        }

    }


    //Stops food healing while bleeding
    @Inject(method = "canFoodHeal()Z", at = @At("RETURN"), cancellable = true)
    public void noHealing(CallbackInfoReturnable<Boolean> ret) {
        PlayerEntity player = ((PlayerEntity) (Object) this);
        if(ImBleeding.config.bleedingStopsFoodHealing){
            ret.setReturnValue(BleedingHelper.shouldStopFoodHealing(player));
        }
    }

    //Gives blindness if player has 1 hp or lower
    @Inject(method = "tick()V", at = @At("TAIL"))
    public void setBlindness(CallbackInfo info){
        PlayerEntity player = ((PlayerEntity) (Object) this);
        BleedingHelper.applyBlindnessToPlayer(player);
    }

}
