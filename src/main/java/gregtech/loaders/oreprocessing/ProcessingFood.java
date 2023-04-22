package gregtech.loaders.oreprocessing;

import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sBenderRecipes;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sMixerRecipes;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sPressRecipes;
import static gregtech.api.util.GT_RecipeBuilder.SECONDS;
import static gregtech.api.util.GT_RecipeBuilder.TICKS;

import net.minecraft.item.ItemStack;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;

public class ProcessingFood implements gregtech.api.interfaces.IOreRecipeRegistrator {

    public ProcessingFood() {
        OrePrefixes.food.add(this);
    }

    @Override
    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName,
        ItemStack aStack) {
        switch (aOreDictName) {
            case "foodCheese" -> {
                registerSlicerRecipes(aStack);
                GT_OreDictUnificator.addItemData(aStack, new gregtech.api.objects.ItemData(Materials.Cheese, 3628800L));
            }
            case "foodDough" -> {
                GT_ModHandler.removeFurnaceSmelting(aStack);
                registerBenderRecipes(aStack);
                registerMixerRecipes(aStack);
                registerFormingPressRecipes(aStack);
            }
        }
    }

    private void registerSlicerRecipes(ItemStack stack) {
        GT_Values.RA
            .addSlicerRecipe(stack, ItemList.Shape_Slicer_Flat.get(0L), ItemList.Food_Sliced_Cheese.get(4L), 64, 4);
    }

    private void registerBenderRecipes(ItemStack stack) {
        GT_Values.RA.stdBuilder()
            .itemInputs(GT_Utility.copyAmount(1L, stack))
            .itemOutputs(ItemList.Food_Flat_Dough.get(1L))
            .noFluidInputs()
            .noFluidOutputs()
            .duration(16 * TICKS)
            .eut(4)
            .addTo(sBenderRecipes);
    }

    private void registerMixerRecipes(ItemStack stack) {
        GT_Values.RA.stdBuilder()
            .itemInputs(stack, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sugar, 1L))
            .itemOutputs(ItemList.Food_Dough_Sugar.get(2L))
            .noFluidInputs()
            .noFluidOutputs()
            .duration(1 * SECONDS + 12 * TICKS)
            .eut(8)
            .addTo(sMixerRecipes);

        GT_Values.RA.stdBuilder()
            .itemInputs(stack, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cocoa, 1L))
            .itemOutputs(ItemList.Food_Dough_Chocolate.get(2L))
            .noFluidInputs()
            .noFluidOutputs()
            .duration(1 * SECONDS + 12 * TICKS)
            .eut(8)
            .addTo(sMixerRecipes);

        GT_Values.RA.stdBuilder()
            .itemInputs(stack, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Chocolate, 1L))
            .itemOutputs(ItemList.Food_Dough_Chocolate.get(2L))
            .noFluidInputs()
            .noFluidOutputs()
            .duration(1 * SECONDS + 12 * TICKS)
            .eut(8)
            .addTo(sMixerRecipes);
    }

    private void registerFormingPressRecipes(ItemStack stack) {

        GT_Values.RA.stdBuilder()
            .itemInputs(GT_Utility.copyAmount(1L, stack), ItemList.Shape_Mold_Bun.get(0L))
            .itemOutputs(ItemList.Food_Raw_Bun.get(1L))
            .noFluidInputs()
            .noFluidOutputs()
            .duration(6 * SECONDS + 8 * TICKS)
            .eut(4)
            .addTo(sPressRecipes);

        GT_Values.RA.stdBuilder()
            .itemInputs(GT_Utility.copyAmount(2L, stack), ItemList.Shape_Mold_Bread.get(0L))
            .itemOutputs(ItemList.Food_Raw_Bread.get(1L))
            .noFluidInputs()
            .noFluidOutputs()
            .duration(12 * SECONDS + 16 * TICKS)
            .eut(4)
            .addTo(sPressRecipes);

        GT_Values.RA.stdBuilder()
            .itemInputs(GT_Utility.copyAmount(3L, stack), ItemList.Shape_Mold_Baguette.get(0L))
            .itemOutputs(ItemList.Food_Raw_Baguette.get(1L))
            .noFluidInputs()
            .noFluidOutputs()
            .duration(19 * SECONDS + 4 * TICKS)
            .eut(4)
            .addTo(sPressRecipes);
    }
}
