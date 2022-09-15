package net.fryc.imbleeding.effects;

import net.fryc.imbleeding.entity.damage.BleedDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class BleedoutEffect extends StatusEffect {

    protected BleedoutEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.world.isClient()) {
            if(pLivingEntity.getActiveStatusEffects().containsKey(ModEffects.BLEED_EFFECT) && pLivingEntity.getActiveStatusEffects().get(ModEffects.BLEEDOUT).getDuration() > 1800) pLivingEntity.damage(BleedDamageSource.bleed(), 200);
        }

        super.applyUpdateEffect(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int pDuration, int pAmplifier) {
        int i;
        if (this == ModEffects.BLEEDOUT) {
            i = 25 >> pAmplifier;
            if (i > 0) {
                return pDuration % i == 0;
            } else {
                return true;
            }
        }
        else return true;
    }
}
