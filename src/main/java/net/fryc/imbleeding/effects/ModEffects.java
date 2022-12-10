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

    static StatusEffect bleedout = new BleedoutEffect(StatusEffectCategory.HARMFUL, 16262179).addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, "7D6F0BA2-1186-46AC-B896-C61C5CEE99CC", -12.0, EntityAttributeModifier.Operation.ADDITION);
    static StatusEffect bleed = new BleedEffect(StatusEffectCategory.HARMFUL, 16262179);
    static StatusEffect healthloss = new HealthLossEffect(StatusEffectCategory.HARMFUL, 16262179).addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, "8D6F0BA2-1186-46AC-B896-C61C5CEE99CC", -2.0, EntityAttributeModifier.Operation.ADDITION);

    public static StatusEffect registerStatusEffect(String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(ImBleeding.MOD_ID, name),
                effect);
    }



    public static void registerEffects() {
        BLEED_EFFECT = registerStatusEffect("bleed", bleed);
        BLEEDOUT = registerStatusEffect("bleedout" , bleedout);
        HEALTH_LOSS = registerStatusEffect("health_loss", healthloss);
    }
}
