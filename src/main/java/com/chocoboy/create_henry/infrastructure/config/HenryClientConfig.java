package com.chocoboy.create_henry.infrastructure.config;

import net.createmod.catnip.config.ConfigBase;

public class HenryClientConfig extends ConfigBase {

	public final ConfigGroup client = group(0, "client",
			Comments.client);

	// custom fluid fog
	public final ConfigGroup fluidFogSettings = group(1, "fluidFogSettings", Comments.fluidFogSettings);
	public final ConfigFloat sapTransparencyMultiplier =
		f(1, .125f, 128, "sap_transparency", Comments.sapTransparencyMultiplier);
	public final ConfigInt sapFogColor =
		i(0xE2B824, 0, 0xFFFFFF, "sap_fog_color", Comments.fogColor);
	public final ConfigFloat chocolateTransparencyMultiplier =
		f(1, .125f, 128, "chocolate_milkshake_transparency", Comments.milkshakeTransparencyMultiplier);
	public final ConfigInt chocolateFogColor =
		i(0x8B4A2F, 0, 0xFFFFFF, "chocolate_milkshake_fog_color", Comments.fogColor);
	public final ConfigFloat vanillaTransparencyMultiplier =
		f(1, .125f, 128, "vanilla_milkshake_transparency", Comments.milkshakeTransparencyMultiplier);
	public final ConfigInt vanillaFogColor =
		i(0xFFE7A3, 0, 0xFFFFFF, "vanilla_milkshake_fog_color", Comments.fogColor);
	public final ConfigFloat strawberryTransparencyMultiplier =
		f(1, .125f, 128, "strawberry_milkshake_transparency", Comments.milkshakeTransparencyMultiplier);
	public final ConfigInt strawberryFogColor =
		i(0xFF8FB1, 0, 0xFFFFFF, "strawberry_milkshake_fog_color", Comments.fogColor);
	public final ConfigFloat glowberryTransparencyMultiplier =
		f(1, .125f, 128, "glowberry_milkshake_transparency", Comments.milkshakeTransparencyMultiplier);
	public final ConfigInt glowberryFogColor =
		i(0xFFF066, 0, 0xFFFFFF, "glowberry_milkshake_fog_color", Comments.fogColor);
	public final ConfigFloat pumpkinTransparencyMultiplier =
		f(1, .125f, 128, "pumpkin_milkshake_transparency", Comments.milkshakeTransparencyMultiplier);
	public final ConfigInt pumpkinFogColor =
		i(0xFF9A3C, 0, 0xFFFFFF, "pumpkin_milkshake_fog_color", Comments.fogColor);

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
		static String milkshakeTransparencyMultiplier = "The vision range through this milkshake will be multiplied by this factor";
		static String fogColor = "Submerged fog color as a 24-bit RGB integer (e.g. 0xFF0000 = 16711680 = red). Requires restart.";
	}

}
