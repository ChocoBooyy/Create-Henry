package com.chocoboy.create_henry.registry;

import com.chocoboy.create_henry.HenryCreate;
import com.simibubi.create.content.fluids.VirtualFluid;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.FluidEntry;

public class HenryFluids {
	private static final CreateRegistrate REGISTRATE = HenryCreate.registrate();

	static {
		REGISTRATE.setCreativeTab(HenryCreativeModeTabs.BASE_CREATIVE_TAB);
	}

	public static final FluidEntry<VirtualFluid> SAP = REGISTRATE.virtualFluid("sap")
			.lang("Sap Liquid")
			.tag(HenryTags.forgeFluidTag("sap"))
			.register();

	// Load this class

	public static void register() {
	}

}
