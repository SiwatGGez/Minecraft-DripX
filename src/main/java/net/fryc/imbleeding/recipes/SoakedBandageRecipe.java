package net.fryc.imbleeding.recipes;

import net.fryc.imbleeding.items.ModItems;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.World;

public class SoakedBandageRecipe extends SpecialCraftingRecipe {

    private static final int BANDAGES_COUNT = 2;

    public SoakedBandageRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(RecipeInputInventory inventory, World world) {
        int bandagesPresent = 0;
        int potionsPresent = 0;
        for(ItemStack stack : inventory.getInputStacks()){
            if(stack.isOf(ModItems.BANDAGE)){
                bandagesPresent++;
            }
            else if(stack.isOf(Items.LINGERING_POTION)){
                potionsPresent++;
            }
            else if(!stack.isEmpty()){
                return false;
            }
        }
        return bandagesPresent == BANDAGES_COUNT && potionsPresent == 1;
    }

    @Override
    public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
        ItemStack potion = ItemStack.EMPTY;
        for(ItemStack stack : inventory.getInputStacks()){
            if(stack.isOf(Items.LINGERING_POTION)){
                potion = stack;
            }
        }
        if(potion.isEmpty()) return potion;

        ItemStack bandages = new ItemStack(ModItems.SOAKED_BANDAGE, BANDAGES_COUNT);
        PotionUtil.setPotion(bandages, PotionUtil.getPotion(potion));
        PotionUtil.setCustomPotionEffects(bandages, PotionUtil.getCustomPotionEffects(potion));

        return bandages;
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.SOAKED_BANDAGE;
    }
}
