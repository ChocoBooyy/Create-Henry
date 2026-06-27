package com.chocoboy.create_henry.content.rei;

import com.simibubi.create.compat.rei.category.BasinCategory;
import com.simibubi.create.compat.rei.category.CreateRecipeCategory;
import com.simibubi.create.compat.rei.category.animations.AnimatedBlazeBurner;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;

import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

/**
 * REI display for Henry's hydraulic compacting (a basin recipe). Reuses Create's basin layout and
 * draws Henry's animated Hydraulic Press on top, mirroring the JEI HydraulicCategory.
 */
public class HydraulicReiCategory extends BasinCategory {

    private final AnimatedHydraulicRei press = new AnimatedHydraulicRei(true);
    private final AnimatedBlazeBurner heater = new AnimatedBlazeBurner();

    public HydraulicReiCategory(CreateRecipeCategory.Info<BasinRecipe> info) {
        super(info, true);
    }

    @Override
    public void draw(@NotNull BasinRecipe recipe, @NotNull GuiGraphics graphics, double mouseX, double mouseY) {
        super.draw(recipe, graphics, mouseX, mouseY);

        HeatCondition requiredHeat = recipe.getRequiredHeat();
        if (requiredHeat != HeatCondition.NONE)
            heater.withHeat(requiredHeat.visualizeAsBlazeBurner())
                    .draw(graphics, getDisplayWidth(null) / 2 + 3, 55);
        press.draw(graphics, getDisplayWidth(null) / 2 + 3, 34);
    }
}
