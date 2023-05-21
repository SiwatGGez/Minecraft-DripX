package net.fryc.imbleeding.mixin;

import net.fryc.imbleeding.util.ClearEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MilkBucketItem.class)
abstract class MilkBucketMixin extends Item {
    public MilkBucketMixin(Settings settings) {
        super(settings);
    }

    //Prevents milk from removing bleed and bleedout
    @Redirect(method = "finishUsing(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/item/ItemStack;", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z"))
    private boolean dontRemoveBleeding(LivingEntity user, ItemStack stack, World world) {
        return ((ClearEffects) user).clearStatusEffectsExceptBleeding();
    }



}
