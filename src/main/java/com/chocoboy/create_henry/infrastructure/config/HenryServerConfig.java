package com.chocoboy.create_henry.infrastructure.config;

import net.createmod.catnip.config.ConfigBase;

public class HenryServerConfig extends ConfigBase {

	public final ConfigGroup infrastructure = group(0, "infrastructure", Comments.infrastructure);

	public final HenryRecipesConfig recipes = nested(0, HenryRecipesConfig::new, Comments.recipes);
	public final HenryKineticsConfig kinetics = nested(0, HenryKineticsConfig::new, Comments.kinetics);

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
