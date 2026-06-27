package com.chocoboy.create_henry.content.emi;

import com.chocoboy.create_henry.content.recipeviewer.HenryRecipeRenders;
import com.simibubi.create.compat.emi.CreateEmiAnimations;
import com.simibubi.create.compat.emi.recipes.basin.BasinEmiRecipe;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.widget.WidgetHolder;

/**
 * EMI display for Henry's hydraulic compacting (a basin recipe). Reuses Create's basin layout and
 * draws Henry's Hydraulic Press on top, mirroring the JEI HydraulicCategory.
 */
public class HenryHydraulicEmiRecipe extends BasinEmiRecipe {

    public HenryHydraulicEmiRecipe(EmiRecipeCategory category, BasinRecipe recipe) {
        super(category, recipe, true);
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        super.addWidgets(widgets);

        HeatCondition requiredHeat = recipe.getRequiredHeat();
        if (requiredHeat != HeatCondition.NONE) {
            CreateEmiAnimations.addBlazeBurner(widgets, widgets.getWidth() / 2 + 3, 55, requiredHeat.visualizeAsBlazeBurner());
        }
        widgets.addDrawable(widgets.getWidth() / 2 + 3, 40, 0, 0, (graphics, mouseX, mouseY, delta) ->
                HenryRecipeRenders.drawHydraulicPress(graphics, CreateEmiAnimations.DEFAULT_LIGHTING, true, 0, 0));
    }
}
