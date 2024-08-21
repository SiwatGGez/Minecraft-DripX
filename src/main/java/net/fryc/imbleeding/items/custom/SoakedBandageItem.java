package net.fryc.imbleeding.items.custom;

import com.google.common.collect.Sets;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.List;

public class SoakedBandageItem extends BandageItem {

    public static final float SOAKED_BANDAGE_DURATION_MULTIPLIER = 0.60f;
    public static final HashSet<Potion> SPECIAL_TRANSLATION_POTIONS = Sets.newHashSet(
            Potions.WATER.value(),
            Potions.NIGHT_VISION.value(),
            Potions.LONG_NIGHT_VISION.value(),
            Potions.INVISIBILITY.value(),
            Potions.LONG_INVISIBILITY.value(),
            Potions.LEAPING.value(),
            Potions.LONG_LEAPING.value(),
            Potions.STRONG_LEAPING.value(),
            Potions.FIRE_RESISTANCE.value(),
            Potions.LONG_FIRE_RESISTANCE.value(),
            Potions.SWIFTNESS.value(),
            Potions.LONG_SWIFTNESS.value(),
            Potions.STRONG_SWIFTNESS.value(),
            Potions.SLOWNESS.value(),
            Potions.LONG_SLOWNESS.value(),
            Potions.STRONG_SLOWNESS.value(),
            Potions.TURTLE_MASTER.value(),
            Potions.LONG_TURTLE_MASTER.value(),
            Potions.STRONG_TURTLE_MASTER.value(),
            Potions.WATER_BREATHING.value(),
            Potions.LONG_WATER_BREATHING.value(),
            Potions.HEALING.value(),
            Potions.STRONG_HEALING.value(),
            Potions.HARMING.value(),
            Potions.STRONG_HARMING.value(),
            Potions.POISON.value(),
            Potions.LONG_POISON.value(),
            Potions.STRONG_POISON.value(),
            Potions.REGENERATION.value(),
            Potions.LONG_REGENERATION.value(),
            Potions.STRONG_REGENERATION.value(),
            Potions.STRENGTH.value(),
            Potions.LONG_STRENGTH.value(),
            Potions.STRONG_STRENGTH.value(),
            Potions.WEAKNESS.value(),
            Potions.LONG_WEAKNESS.value(),
            Potions.LUCK.value(),
            Potions.SLOW_FALLING.value(),
            Potions.LONG_SLOW_FALLING.value()
    );

    public SoakedBandageItem(Settings settings) {
        super(settings);
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient) {
            PotionContentsComponent potionContentsComponent = (PotionContentsComponent)stack.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT);
            potionContentsComponent.forEachEffect((effect) -> {
                if (((StatusEffect)effect.getEffectType().value()).isInstant()) {
                    ((StatusEffect)effect.getEffectType().value()).applyInstantEffect(user, user, user, effect.getAmplifier(), 1.0);
                } else {
                    user.addStatusEffect(new StatusEffectInstance(effect.getEffectType(), (int)(effect.getDuration()*SOAKED_BANDAGE_DURATION_MULTIPLIER), effect.getAmplifier(), effect.isAmbient(), effect.shouldShowParticles(), effect.shouldShowIcon()));
                }

            });
        }
        return super.finishUsing(stack, world, user);
    }

    public ItemStack getDefaultStack() {
        return PotionContentsComponent.createStack(this, Potions.HEALING);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        PotionContentsComponent potionContentsComponent = stack.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT);
        if(potionContentsComponent.potion().isPresent()){
            if(potionContentsComponent.potion().get() != Potions.WATER){
                potionContentsComponent.buildTooltip(tooltip::add, SOAKED_BANDAGE_DURATION_MULTIPLIER, context.getUpdateTickRate());
            }
        }
    }

    public String getTranslationKey(ItemStack stack) {
        PotionContentsComponent potionContentsComponent = stack.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT);
        if(potionContentsComponent.potion().isPresent()){
            potionContentsComponent.potion().get().value();
            return SPECIAL_TRANSLATION_POTIONS.contains(potionContentsComponent.potion().get().value()) ? Potion.finishTranslationKey(potionContentsComponent.potion(), this.getTranslationKey() + ".effect.") : this.getTranslationKey();
        }
        return this.getTranslationKey();
    }

}
