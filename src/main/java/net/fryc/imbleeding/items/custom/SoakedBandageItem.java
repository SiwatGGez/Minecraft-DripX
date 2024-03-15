package net.fryc.imbleeding.items.custom;

import com.google.common.collect.Sets;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;

public class SoakedBandageItem extends BandageItem {

    public static final float SOAKED_BANDAGE_DURATION_MULTIPLIER = 0.60f;
    public static final HashSet<Potion> SPECIAL_TRANSLATION_POTIONS = Sets.newHashSet(
            Potions.WATER,
            Potions.NIGHT_VISION,
            Potions.LONG_NIGHT_VISION,
            Potions.INVISIBILITY,
            Potions.LONG_INVISIBILITY,
            Potions.LEAPING,
            Potions.LONG_LEAPING,
            Potions.STRONG_LEAPING,
            Potions.FIRE_RESISTANCE,
            Potions.LONG_FIRE_RESISTANCE,
            Potions.SWIFTNESS,
            Potions.LONG_SWIFTNESS,
            Potions.STRONG_SWIFTNESS,
            Potions.SLOWNESS,
            Potions.LONG_SLOWNESS,
            Potions.STRONG_SLOWNESS,
            Potions.TURTLE_MASTER,
            Potions.LONG_TURTLE_MASTER,
            Potions.STRONG_TURTLE_MASTER,
            Potions.WATER_BREATHING,
            Potions.LONG_WATER_BREATHING,
            Potions.HEALING,
            Potions.STRONG_HEALING,
            Potions.HARMING,
            Potions.STRONG_HARMING,
            Potions.POISON,
            Potions.LONG_POISON,
            Potions.STRONG_POISON,
            Potions.REGENERATION,
            Potions.LONG_REGENERATION,
            Potions.STRONG_REGENERATION,
            Potions.STRENGTH,
            Potions.LONG_STRENGTH,
            Potions.STRONG_STRENGTH,
            Potions.WEAKNESS,
            Potions.LONG_WEAKNESS,
            Potions.LUCK,
            Potions.SLOW_FALLING,
            Potions.LONG_SLOW_FALLING
    );

    public SoakedBandageItem(Settings settings) {
        super(settings);
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient) {
            Potion potion = PotionUtil.getPotion(stack);
            if(potion == Potions.WATER){
                user.extinguishWithSound();
            }
            else if(potion != Potions.EMPTY){
                for(StatusEffectInstance effect : potion.getEffects()){
                    if(effect.getEffectType().isInstant()){
                        effect.getEffectType().applyInstantEffect(null, null, user, effect.getAmplifier(), 1.0);
                    }
                    else {
                        user.addStatusEffect(new StatusEffectInstance(effect.getEffectType(), (int)(effect.getDuration()*SOAKED_BANDAGE_DURATION_MULTIPLIER), effect.getAmplifier(), effect.isAmbient(), effect.shouldShowParticles(), effect.shouldShowIcon()));
                    }
                }
            }
        }
        return super.finishUsing(stack, world, user);
    }

    public ItemStack getDefaultStack() {
        return PotionUtil.setPotion(super.getDefaultStack(), Potions.HEALING);
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(PotionUtil.getPotion(stack) != Potions.WATER) PotionUtil.buildTooltip(stack, tooltip, SOAKED_BANDAGE_DURATION_MULTIPLIER);
    }

    public String getTranslationKey(ItemStack stack) {
        return SPECIAL_TRANSLATION_POTIONS.contains(PotionUtil.getPotion(stack)) ? PotionUtil.getPotion(stack).finishTranslationKey(this.getTranslationKey() + ".effect.") : this.getTranslationKey();
    }

}
