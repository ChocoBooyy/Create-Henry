package com.chocoboy.create_henry.content.rei;

import com.simibubi.create.compat.rei.category.ProcessingViaFanCategory;
import com.simibubi.create.compat.rei.category.animations.AnimatedKinetics;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;

import net.createmod.catnip.gui.element.GuiGameElement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * REI display for Henry's simple fan-processing recipes (sanding, freezing, withering, dragon
 * breathing). Reuses Create's fan rendering and draws the catalyst block behind the fan.
 */
public class FanSimpleBlockReiCategory<T extends ProcessingRecipe<?>> extends ProcessingViaFanCategory.MultiOutput<T> {

    private final BlockState blockState;

    public FanSimpleBlockReiCategory(Info<T> info, Block block) {
        super(info);
        this.blockState = block.defaultBlockState();
    }

    @Override
    protected void renderAttachedBlock(GuiGraphics graphics) {
        GuiGameElement.of(blockState)
                .scale(SCALE)
                .atLocal(0, 0, 2)
                .lighting(AnimatedKinetics.DEFAULT_LIGHTING)
                .render(graphics);
    }
}
