package com.chocoboy.create_henry.infrastructure.config;

import com.simibubi.create.foundation.config.ConfigBase;

public class HServer extends ConfigBase {

	public final ConfigGroup infrastructure = group(0, "infrastructure", Comments.infrastructure);

	public final HRecipes recipes = nested(0, HRecipes::new, Comments.recipes);
	public final HKinetics kinetics = nested(0, HKinetics::new, Comments.kinetics);

	@Override
	public String getName() {
		return "server";
	}

	private static class Comments {
		static String recipes = "Packmakers' control panel for internal recipe compat";
		static String kinetics = "Parameters and abilities of Create: Henry's kinetic mechanisms";
		static String infrastructure = "The Backbone of Create: Henry";
	}

}
