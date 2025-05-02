package com.chocoboy.create_henry.infrastructure.ponder;

import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.ponder.PonderTag;
import com.chocoboy.create_henry.HenryCreate;

public class HenryPonderTags {

	public static final PonderTag
			Henry = create("create_henry").item(AllItems.PRECISION_MECHANISM.get())
			.defaultLang("Create: Henry", "Welcome to the mod!")
			.addToIndex();

	private static PonderTag create(String id) {
		return new PonderTag(HenryCreate.asResource(id));
	}

	public static void register() {
		// Add items to tags here

	}

}