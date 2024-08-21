package net.fryc.imbleeding.effects;

import net.fryc.imbleeding.ImBleeding;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static RegistryEntry<StatusEffect> BLEED_EFFECT;
    public static RegistryEntry<StatusEffect> BLEEDOUT;
    public static RegistryEntry<StatusEffect> HEALTH_LOSS;

    public static RegistryEntry<StatusEffect> BROKEN;

    private static final StatusEffect bleedout = new BleedoutEffect(StatusEffectCategory.HARMFUL, 16262179).addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, Identifier.of(ImBleeding.MOD_ID, "effect.bleedout"), -12.0, EntityAttributeModifier.Operation.ADD_VALUE);
    private static final StatusEffect bleed = new BleedEffect(StatusEffectCategory.HARMFUL, 16262179);
    private static final StatusEffect healthloss = new HealthLossEffect(StatusEffectCategory.HARMFUL, 16262179).addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, Identifier.of(ImBleeding.MOD_ID, "effect.health_loss"), -2.0, EntityAttributeModifier.Operation.ADD_VALUE);
    private static final StatusEffect broken = new BrokenEffect(StatusEffectCategory.HARMFUL, 9154528).addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,Identifier.of(ImBleeding.MOD_ID, "effect.broken"), -0.15, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);


    private static RegistryEntry<StatusEffect> registerStatusEffect(String id, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(ImBleeding.MOD_ID, id), statusEffect);
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
