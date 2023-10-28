package net.fryc.imbleeding.effects;

import net.fryc.imbleeding.ImBleeding;
import net.fryc.imbleeding.damage.ModDamageTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;

public class BleedEffect extends StatusEffect {
        public BleedEffect(StatusEffectCategory statusEffectCategory, int color) {
            super(statusEffectCategory, color);
        }

        @Override
        public void applyUpdateEffect(LivingEntity pLivingEntity, int pAmplifier) {
            if (!pLivingEntity.getWorld().isClient()) {

                if(pLivingEntity.getHealth() > 0.5F){
                    pLivingEntity.damage(ModDamageTypes.of(pLivingEntity.getWorld(), ModDamageTypes.BLEEDING_DAMAGE), 0.5F);
                }
                else if(!pLivingEntity.getActiveStatusEffects().containsKey(ModEffects.BLEEDOUT)){
                    pLivingEntity.addStatusEffect(new StatusEffectInstance(ModEffects.BLEEDOUT, ImBleeding.config.bleedoutLength/2, 0, false, false, true));
                }
                else{
                    pLivingEntity.addStatusEffect(new StatusEffectInstance(ModEffects.BLEEDOUT, pLivingEntity.getActiveStatusEffects().get(ModEffects.BLEEDOUT).getDuration() + ImBleeding.config.bleedoutLength, 0, false, false, true));
                }

            }

            super.applyUpdateEffect(pLivingEntity, pAmplifier);
        }

        @Override
        public boolean canApplyUpdateEffect(int pDuration, int pAmplifier) {
            int i;
            if (this == ModEffects.BLEED_EFFECT) {
                i = 120 >> pAmplifier;
                if (i > 0) {
                    return pDuration % i == 0;
                } else {
                    return true;
                }
            }
            else return true;
        }
}
