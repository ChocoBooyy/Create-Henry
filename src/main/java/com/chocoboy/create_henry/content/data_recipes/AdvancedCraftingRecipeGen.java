package com.chocoboy.create_henry.content.data_recipes;

import com.google.common.base.Supplier;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import com.simibubi.create.foundation.data.recipe.MechanicalCraftingRecipeBuilder;
import com.simibubi.create.foundation.utility.RegisteredObjects;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import com.chocoboy.create_henry.HenryCreate;
import com.chocoboy.create_henry.registry.HenryBlocks;
import com.chocoboy.create_henry.registry.HenryItems;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.UnaryOperator;

@ParametersAreNonnullByDefault
@SuppressWarnings({"unused", "all"})
public class AdvancedCraftingRecipeGen extends CreateRecipeProvider {

    GeneratedRecipe
            //SEQUENCED ASSEMBLY RECIPE
            KINETIC_MECHANISM = createSequencedAssembly("kinetic_mechanism", b -> b.require(AllItems.IRON_SHEET.get())
            .transitionTo(HenryItems.INCOMPLETE_KINETIC_MECHANISM.get())
            .addOutput(HenryItems.KINETIC_MECHANISM.get(), 480)
            .addOutput(AllItems.ANDESITE_ALLOY.get(), 16)
            .addOutput(AllItems.IRON_SHEET.get(), 8)
            .addOutput(AllBlocks.COGWHEEL.get(), 5)
            .addOutput(AllItems.SUPER_GLUE.asStack(), 4)
            .addOutput(AllItems.ZINC_NUGGET.get(), 3)
            .addOutput(AllBlocks.SHAFT.get(), 2)
            .addOutput(AllItems.CRUSHED_IRON.get(), 2)
            .addOutput(Items.IRON_INGOT, 1)
            .loops(2)
            .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ANDESITE_ALLOY.get()))
            .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllBlocks.COGWHEEL.get()))
            .addStep(DeployerApplicationRecipe::new, rb -> rb.require(Items.SLIME_BALL))
            .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ZINC_NUGGET.get()))
    ),


    //MECHANICAL CRAFTING RECIPE

    HYDRAULIC_PRESS = createMechanicalCrafting(HenryBlocks.HYDRAULIC_PRESS::get).returns(1)
            .recipe(b -> b
                    .key('P', AllBlocks.FLUID_PIPE.get())
                    .key('M', AllBlocks.MECHANICAL_PRESS.get())
                    .key('H', HenryBlocks.HYDRAULIC_CASING.get())
                    .key('C', Items.COPPER_BLOCK)
                    .patternLine(" P ")
                    .patternLine(" H ")
                    .patternLine("CMC")
            )

    ;

    public AdvancedCraftingRecipeGen(PackOutput p_i48262_1_) {
        super(p_i48262_1_);
    }

    protected GeneratedRecipe createSequencedAssembly(String name, UnaryOperator<SequencedAssemblyRecipeBuilder> transform) {
        GeneratedRecipe generatedRecipe =
                c -> transform.apply(new SequencedAssemblyRecipeBuilder(HenryCreate.asResource(name)))
                        .build(c);
        all.add(generatedRecipe);
        return generatedRecipe;
    }

    GeneratedRecipeBuilder createMechanicalCrafting(Supplier<ItemLike> result) {
        return new GeneratedRecipeBuilder(result);
    }

    class GeneratedRecipeBuilder {

        private String suffix;
        private Supplier<ItemLike> result;
        private int amount;

        public GeneratedRecipeBuilder(Supplier<ItemLike> result) {
            this.suffix = "";
            this.result = result;
            this.amount = 1;
        }

        GeneratedRecipeBuilder returns(int amount) {
            this.amount = amount;
            return this;
        }

        GeneratedRecipeBuilder withSuffix(String suffix) {
            this.suffix = suffix;
            return this;
        }

        GeneratedRecipe recipe(UnaryOperator<MechanicalCraftingRecipeBuilder> builder) {
            return register(consumer -> {
                MechanicalCraftingRecipeBuilder b =
                        builder.apply(MechanicalCraftingRecipeBuilder.shapedRecipe(result.get(), amount));
                ResourceLocation location = HenryCreate.asResource("mechanical_crafting/" + RegisteredObjects.getKeyOrThrow(result.get()
                                .asItem())
                        .getPath() + suffix);
                b.build(consumer, location);
            });
        }
    }

}