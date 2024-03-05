package net.fryc.imbleeding.effects;

import net.fryc.imbleeding.ImBleeding;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static StatusEffect BLEED_EFFECT;
    public static StatusEffect BLEEDOUT;
    public static StatusEffect HEALTH_LOSS;

    public static StatusEffect BROKEN;

    private static final StatusEffect bleedout = new BleedoutEffect(StatusEffectCategory.HARMFUL, 16262179).addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, "7D6F0BA2-1186-46AC-B896-C61C5CEE99CC", -12.0, EntityAttributeModifier.Operation.ADDITION);
    private static final StatusEffect bleed = new BleedEffect(StatusEffectCategory.HARMFUL, 16262179);
    private static final StatusEffect healthloss = new HealthLossEffect(StatusEffectCategory.HARMFUL, 16262179).addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, "8D6F0BA2-1186-46AC-B896-C61C5CEE99CC", -2.0, EntityAttributeModifier.Operation.ADDITION);
    private static final StatusEffect broken = new BrokenEffect(StatusEffectCategory.HARMFUL, 9154528).addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,"3104DE5E-6CE8-4080-940E-524CCF161820", -0.15, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);

    public static StatusEffect registerStatusEffect(String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(ImBleeding.MOD_ID, name),
                effect);
    }



    public static void registerEffects() {
        if(BLEED_EFFECT == null){
            BLEED_EFFECT = registerStatusEffect("bleed", bleed);
            BLEEDOUT = registerStatusEffect("bleedout" , bleedout);
            HEALTH_LOSS = registerStatusEffect("health_loss", healthloss);
            BROKEN = registerStatusEffect("broken", broken);
        }
    }
}
