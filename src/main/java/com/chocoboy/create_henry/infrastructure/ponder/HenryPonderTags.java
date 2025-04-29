package com.chocoboy.create_henry.infrastructure.ponder;

import net.minecraft.resources.ResourceLocation;
import com.chocoboy.create_henry.HenryCreate;

public class HenryPonderTags {

	public static final ResourceLocation
			Henry = loc("create_henry");

	private static ResourceLocation loc(String id) {
		return HenryCreate.asResource(id);
	}

	public static void register() {
		// Add items to tags here

	}

}
