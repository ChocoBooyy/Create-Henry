package com.chocoboy.create_henry.registry;

import com.jozufozu.flywheel.core.PartialModel;
import com.chocoboy.create_henry.HenryCreate;

@SuppressWarnings({"all"})
public class HenryPartialModels {

	public static final PartialModel

		EMPTY = block("empty"),
		INDUSTRIAL_FAN_POWER = block("industrial_fan/cog"),
		INDUSTRIAL_FAN_INNER = block("industrial_fan/propeller"),
		HYDRAULIC_PRESS_HEAD = block("hydraulic_press/head"),
		ENGINE_PISTON = block("furnace_engine/piston"),
		ENGINE_LINKAGE = block("furnace_engine/linkage"),
		ENGINE_CONNECTOR = block("furnace_engine/shaft_connector"),
		GAUGE_DIAL = block("gauge/dial"),
		GAUGE_INDICATOR = block("gauge/indicator"),
		GAUGE_HEAD = block("gauge/multimeter/head");

	private static PartialModel block(String path) {
		return new PartialModel(HenryCreate.asResource("block/" + path));
	}


	public static void init() {
	}
}
