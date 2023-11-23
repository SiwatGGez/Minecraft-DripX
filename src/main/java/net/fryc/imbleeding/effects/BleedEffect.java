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
                int multiplier = pAmplifier + 1;
                float damage = multiplier * ImBleeding.config.bleedingDamage;
                if(pLivingEntity.getHealth() > damage){
                    pLivingEntity.damage(ModDamageTypes.of(pLivingEntity.getWorld(), ModDamageTypes.BLEEDING_DAMAGE), damage);
                }
                else if(pLivingEntity.getHealth() > 0.5F){
                    pLivingEntity.damage(ModDamageTypes.of(pLivingEntity.getWorld(), ModDamageTypes.BLEEDING_DAMAGE), pLivingEntity.getHealth()-0.4F);
                }
                else if(!pLivingEntity.getActiveStatusEffects().containsKey(ModEffects.BLEEDOUT)){
                    pLivingEntity.addStatusEffect(new StatusEffectInstance(ModEffects.BLEEDOUT, (ImBleeding.config.bleedoutLength/2) * multiplier, 0, false, false, true));
                }
                else{
                    pLivingEntity.addStatusEffect(new StatusEffectInstance(ModEffects.BLEEDOUT, pLivingEntity.getActiveStatusEffects().get(ModEffects.BLEEDOUT).getDuration() + ImBleeding.config.bleedoutLength * multiplier, 0, false, false, true));
                }

            }

            super.applyUpdateEffect(pLivingEntity, pAmplifier);
        }

        @Override
        public boolean canApplyUpdateEffect(int pDuration, int pAmplifier) {
            int i;
            if (this == ModEffects.BLEED_EFFECT) {
                i = ImBleeding.config.bleedingDamageFrequency;
                return pDuration % i == 0;
            }
            else return true;
        }

        public boolean isUnremovable() {
            return true;
        }
}
