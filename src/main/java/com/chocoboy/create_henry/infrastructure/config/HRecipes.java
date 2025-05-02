package com.chocoboy.create_henry.infrastructure.config;

import com.simibubi.create.foundation.config.ConfigBase;

public class HRecipes extends ConfigBase {

	public final ConfigBool hydraulicBulkPressing = b(true, "hydraulicBulkPressing", Comments.hydraulicBulkPressing);
	public final ConfigInt hydraulicLavaDrainPressing = i(250, 1, 1000, "hydraulicLavaDrainPressing", Comments.hydraulicLavaDrainPressing);
	public final ConfigInt hydraulicFluidDrainPressing = i(1000, 1, 1000, "hydraulicFluidDrainPressing", Comments.hydraulicFluidDrainPressing);

	@Override
	public String getName() {
		return "recipes";
	}

	private static class Comments {
		static String hydraulicBulkPressing = "Allow the Hydraulic Press to process entire stacks at a time.";
		static String hydraulicLavaDrainPressing = "Value Hydraulic Press to drain amount of lava of each bonk.";
		static String hydraulicFluidDrainPressing = "Value Hydraulic Press to drain amount of fluid of each bonk.";
	}

}
