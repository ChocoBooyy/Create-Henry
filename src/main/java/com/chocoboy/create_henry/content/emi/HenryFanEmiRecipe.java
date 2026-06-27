package com.chocoboy.create_henry.content.emi;

import com.simibubi.create.compat.emi.CreateEmiAnimations;
import com.simibubi.create.compat.emi.recipes.fan.FanEmiRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import net.createmod.catnip.gui.element.GuiGameElement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * EMI display for Henry's fan-processing recipes (sanding, freezing, withering, dragon breathing).
 * Reuses Create's fan rendering and just draws the catalyst block behind the fan. The Encased Fan is
 * a valid catalyst for these recipes (see HenryJEI), so Create's encased-fan animation is accurate.
 */
public class HenryFanEmiRecipe extends FanEmiRecipe.MultiOutput<ProcessingRecipe<?>> {

    private final BlockState block;

    public HenryFanEmiRecipe(EmiRecipeCategory category, ProcessingRecipe<?> recipe, Block block) {
        super(category, recipe);
        this.block = block.defaultBlockState();
    }

    @Override
    protected void renderAttachedBlock(GuiGraphics graphics) {
        GuiGameElement.of(block)
                .scale(SCALE)
                .atLocal(0, 0, 2)
                .lighting(CreateEmiAnimations.DEFAULT_LIGHTING)
                .render(graphics);
    }
}
