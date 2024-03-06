package net.fryc.imbleeding.items.custom;

import net.fryc.imbleeding.effects.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class SplintItem extends Item {
    public SplintItem(Settings settings) {
        super(settings);
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);
        if (!world.isClient) {
            if(user.hasStatusEffect(ModEffects.BROKEN)){
                int duration = user.getActiveStatusEffects().get(ModEffects.BROKEN).getDuration();
                user.removeStatusEffect(ModEffects.BROKEN);
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, duration, 0, false, false, true));
            }


            if(!((PlayerEntity)user).getAbilities().creativeMode) stack.setCount(stack.getCount() - 1);
            user.getWorld().playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, SoundCategory.PLAYERS, 1.0F, 0.75F);

        }
        return stack;
    }

    public int getMaxUseTime(ItemStack stack) {
        return 60;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }
}
