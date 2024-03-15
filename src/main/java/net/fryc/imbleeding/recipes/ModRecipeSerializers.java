package net.fryc.imbleeding.recipes;

import net.fryc.imbleeding.ImBleeding;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipeSerializers {

    public static final RecipeSerializer<SoakedBandageRecipe> SOAKED_BANDAGE = (RecipeSerializer)Registry.register(Registries.RECIPE_SERIALIZER,
            new Identifier(ImBleeding.MOD_ID,"crafting_special_soaked_bandage"), new SpecialRecipeSerializer(SoakedBandageRecipe::new));

    public static void registerModRecipeSerializers(){
    }

}
