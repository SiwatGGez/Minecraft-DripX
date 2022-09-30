package net.fryc.imbleeding.mixin;

import net.fryc.imbleeding.effects.ModEffects;
import net.fryc.imbleeding.gamerules.ModGameRules;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
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
    int toughness;
    //Causes player to bleed after taking damage and gives darkness at low hp
    @Inject(method = "applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V", at = @At("TAIL"))
    public void applyBleed(DamageSource source, float amount, CallbackInfo ci) {
        PlayerEntity player = ((PlayerEntity) (Object) this);
        if(player.getHealth() < 6 && world.getGameRules().getBoolean(ModGameRules.ENABLE_DARKNESS_AT_LOW_HP)){
            if(player.hasStatusEffect(StatusEffects.DARKNESS)){
                if(player.getActiveStatusEffects().get(StatusEffects.DARKNESS).getDuration() < 36) player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 35 , 0));
            }
            else player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 35 , 0));
        }
        toughness = (int) (player.getAttributes().getValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS) * 1.4);
        if(toughness < 1) toughness = 1;
        if(source.getSource() instanceof ArrowEntity){
            int multiplier = (int) (amount * ((20 - player.getArmor())- toughness));
            if(multiplier > 2){
                if(!player.hasStatusEffect(ModEffects.BLEED_EFFECT) || !world.getGameRules().getBoolean(ModGameRules.ENABLE_ARROW_EFFECT_UPGRADING)) player.addStatusEffect(new StatusEffectInstance(ModEffects.BLEED_EFFECT, multiplier*world.getGameRules().getInt(ModGameRules.ARROW_BLEED_LENGTH), 0), source.getAttacker());
                else player.addStatusEffect(new StatusEffectInstance(ModEffects.BLEED_EFFECT, multiplier*(world.getGameRules().getInt(ModGameRules.ARROW_BLEED_LENGTH) + 5), 1), source.getAttacker());
            }

        }
        else if(!source.isProjectile() && source.getAttacker() instanceof SpiderEntity){
            int multiplier = 17 - player.getArmor();
            if(multiplier > 1){
                if(!player.hasStatusEffect(ModEffects.HEALTH_LOSS) || !world.getGameRules().getBoolean(ModGameRules.ENABLE_MELEE_EFFECT_UPGRADING)) player.addStatusEffect(new StatusEffectInstance(ModEffects.HEALTH_LOSS, multiplier*world.getGameRules().getInt(ModGameRules.HEALTH_LOSS_LENGTH), 0), source.getAttacker());
                else player.addStatusEffect(new StatusEffectInstance(ModEffects.HEALTH_LOSS, multiplier*(world.getGameRules().getInt(ModGameRules.HEALTH_LOSS_LENGTH) + 80), 1), source.getAttacker());
            }
        }
        else if(!source.isProjectile() && !source.isExplosive() && source.getAttacker() instanceof LivingEntity){
            int multiplier = (int) (amount * ((21 - player.getArmor())- toughness));
            if(multiplier > 2){
                if(!player.hasStatusEffect(ModEffects.BLEED_EFFECT) || !world.getGameRules().getBoolean(ModGameRules.ENABLE_MELEE_EFFECT_UPGRADING)) player.addStatusEffect(new StatusEffectInstance(ModEffects.BLEED_EFFECT, multiplier*world.getGameRules().getInt(ModGameRules.MELEE_BLEED_LENGTH), 0), source.getAttacker());
                else player.addStatusEffect(new StatusEffectInstance(ModEffects.BLEED_EFFECT, multiplier*(world.getGameRules().getInt(ModGameRules.MELEE_BLEED_LENGTH) + 8), 1), source.getAttacker());
            }
        }

    }

    //Stops food healing while bleeding
    @Inject(method = "canFoodHeal()Z", at = @At("RETURN"), cancellable = true)
    public void noHealing(CallbackInfoReturnable<Boolean> ret) {
        PlayerEntity player = ((PlayerEntity) (Object) this);
        ret.setReturnValue(player.getHealth() > 0.0F && player.getHealth() < player.getMaxHealth() && (!player.getActiveStatusEffects().containsKey(ModEffects.BLEED_EFFECT) || !world.getGameRules().getBoolean(ModGameRules.BLEEDING_STOPS_FOOD_HEALING)));
    }

    //Gives blindness if player has 1 hp or lower
    @Inject(method = "isImmobile()Z", at = @At("HEAD"))
    public void setBlindness(CallbackInfoReturnable<Boolean> ret){
        PlayerEntity player = ((PlayerEntity) (Object) this);
        if(player.getHealth()<=1){
            if(player.hasStatusEffect(StatusEffects.BLINDNESS)){
                if(player.getActiveStatusEffects().get(StatusEffects.BLINDNESS).getDuration() < 25) player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 60 , 0));
            }
            else player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 60 , 0));
        }
    }

}
