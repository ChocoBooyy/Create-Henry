package com.chocoboy.create_henry.infrastructure.config;

import net.createmod.catnip.config.ConfigBase;

public class HClient extends ConfigBase {

	public final ConfigGroup client = group(0, "client",
			Comments.client);

	// custom fluid fog
	public final ConfigGroup fluidFogSettings = group(1, "fluidFogSettings", Comments.fluidFogSettings);
	public final ConfigFloat sapTransparencyMultiplier =
		f(1, .125f, 128, "sap", Comments.sapTransparencyMultiplier);

	//ponder group
	public final ConfigGroup ponder = group(1, "ponder",
			Comments.ponder);

	@Override
	public String getName() {
		return "client";
	}

	private static class Comments {
		static String client = "Client-only settings - If you're looking for general settings, look inside your worlds serverconfig folder!";

		static String ponder = "Ponder settings";
		static String fluidFogSettings = "Configure your vision range when submerged in Create Henry's custom fluids";
		static String sapTransparencyMultiplier = "The vision range through sap will be multiplied by this factor";
	}

}
