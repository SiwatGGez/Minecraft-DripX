package net.fryc.imbleeding.items.custom;

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

import java.util.List;

public class SoakedBandageItem extends BandageItem {

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
                    user.addStatusEffect(new StatusEffectInstance(effect.getEffectType(), (int)(effect.getDuration()*0.7F), effect.getAmplifier(), effect.isAmbient(), effect.shouldShowParticles(), effect.shouldShowIcon()));
                }
            }
        }
        return super.finishUsing(stack, world, user);
    }

    public ItemStack getDefaultStack() {
        return PotionUtil.setPotion(super.getDefaultStack(), Potions.HEALING);
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        PotionUtil.buildTooltip(stack, tooltip, 0.7F);
    }

    public String getTranslationKey(ItemStack stack) {
        return PotionUtil.getPotion(stack).finishTranslationKey(this.getTranslationKey() + ".effect.");
    }

}
