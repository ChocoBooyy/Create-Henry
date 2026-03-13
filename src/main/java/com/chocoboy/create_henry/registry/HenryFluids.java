package com.chocoboy.create_henry.registry;

import com.chocoboy.create_henry.HenryCreate;
import com.chocoboy.create_henry.content.fluids.SolidRenderedPlaceableFluidType;
import com.chocoboy.create_henry.infrastructure.config.HenryConfigs;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class HenryFluids {
	private static final CreateRegistrate REGISTRATE = HenryCreate.registrate();

	static {
		REGISTRATE.setCreativeTab(HenryCreativeModeTabs.BASE_CREATIVE_TAB);
	}

    public static final FluidEntry<ForgeFlowingFluid.Flowing> SAP =
            REGISTRATE.standardFluid("sap",
                            SolidRenderedPlaceableFluidType.create(0xEAAE2F,
                                    () -> 1f / 4f * HenryConfigs.client().sapTransparencyMultiplier.getF()))
                    .lang("Sap")
                    .properties(b -> b.viscosity(2000)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .tag(HenryTags.AllFluidTags.SAP.tag)
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket()
                    .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), new ResourceLocation("forge", "item/bucket")))
                    .tag(HenryTags.forgeItemTag("buckets/sap"))
                    .build()
                    .register();

	// Load this class

	public static void register() {
	}

}
