package net.fryc.imbleeding.items.custom;

import net.fryc.imbleeding.effects.ModEffects;
import net.fryc.imbleeding.items.ModItems;
import net.minecraft.entity.LivingEntity;
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

public class BandageItem extends Item {
    private static final int MAX_USE_TIME = 40;
    public BandageItem(Settings settings) {
        super(settings);
    }
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);
        if (!world.isClient) {
            if(this == ModItems.HONEY_BANDAGE){
                user.removeStatusEffect(StatusEffects.POISON);
                user.removeStatusEffect(ModEffects.HEALTH_LOSS);
                user.heal(3f);
            }
            user.removeStatusEffect(ModEffects.BLEED_EFFECT);
            user.heal(2f);
            if(!((PlayerEntity)user).getAbilities().creativeMode) stack.setCount(stack.getCount() - 1);
            user.world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, SoundCategory.PLAYERS, 1.0F, 1.0F);

        }
        return stack;
    }

    public int getMaxUseTime(ItemStack stack) {
        return 40;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }
}
