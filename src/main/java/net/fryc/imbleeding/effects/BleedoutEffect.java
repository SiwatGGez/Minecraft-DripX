package net.fryc.imbleeding.effects;

import net.fryc.imbleeding.ImBleeding;
import net.fryc.imbleeding.damage.ModDamageTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class BleedoutEffect extends StatusEffect {

    protected BleedoutEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.getWorld().isClient() && ImBleeding.config.bleedoutKills) {
            if(pLivingEntity.hasStatusEffect(ModEffects.BLEED_EFFECT) && pLivingEntity.getActiveStatusEffects().get(ModEffects.BLEEDOUT).getDuration() > ImBleeding.config.bleedoutLength + ImBleeding.config.bleedoutLength/2) pLivingEntity.damage(ModDamageTypes.of(pLivingEntity.getWorld(), ModDamageTypes.BLEEDING_DAMAGE), 200F);
        }

        super.applyUpdateEffect(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int pDuration, int pAmplifier) {
        int i;
        if (this == ModEffects.BLEEDOUT) {
            i = 60 >> pAmplifier;
            if (i > 0) {
                return pDuration % i == 0;
            } else {
                return true;
            }
        }
        else return true;
    }

    public void onApplied(LivingEntity entity, int amplifier) {
        super.onApplied(entity, amplifier);
        if (entity.getHealth() > entity.getMaxHealth()) {
            entity.setHealth(entity.getMaxHealth());
        }
    }

}
