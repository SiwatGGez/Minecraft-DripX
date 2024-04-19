package net.fryc.imbleeding.mixin.compatibility;

import net.fryc.imbleeding.effects.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Pseudo
@Mixin(targets = "com.github.mim1q.minecells.item.HealthFlaskItem")
abstract class MineCellsCompatibilityMixin extends Item {

    public MineCellsCompatibilityMixin(Settings settings) {
        super(settings);
    }

    // injecting into finishUsing method
    @Inject(method = "method_7861(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/item/ItemStack;", at = @At("HEAD"), cancellable = true)
    private void removeBleedingWithHealthFlask(ItemStack stack, World world, LivingEntity user , CallbackInfoReturnable<ItemStack> ret) {
        if(!world.isClient()){
            user.removeStatusEffect(ModEffects.BLEED_EFFECT);
        }
    }

}
