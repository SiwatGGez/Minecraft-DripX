package net.fryc.imbleeding.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoneyBottleItem.class)
abstract class HoneyBottleMixin extends Item {

    public HoneyBottleMixin(Settings settings) {
        super(settings);
    }

    //Honey bottle removes health loss and heals
    @Inject(at = @At("HEAD"), method = "finishUsing(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/item/ItemStack;")
    private void heal(ItemStack stack, World world, LivingEntity user ,CallbackInfoReturnable<Object> info) {
        if (!world.isClient) {
            user.heal(1f);
        }
    }
}
