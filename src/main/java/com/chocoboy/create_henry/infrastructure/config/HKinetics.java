package com.chocoboy.create_henry.infrastructure.config;

import com.simibubi.create.foundation.config.ConfigBase;

public class HKinetics extends ConfigBase {

	public final ConfigGroup fan = group(1, "industrialFan", "Industrial Fan");
	public final ConfigInt fanPushDistance = i(40, 5, "fanPushDistance", Comments.fanPushDistance);
	public final ConfigInt fanPullDistance = i(40, 5, "fanPullDistance", Comments.fanPullDistance);
	public final ConfigInt fanRotationArgmax = i(256, 64, "fanRotationArgmax", Comments.rpm, Comments.fanRotationArgmax);

	public final ConfigGroup stats = group(1, "stats", Comments.stats);

	public final HStress stressValues = nested(1, HStress::new, Comments.stress);

	@Override
	public String getName() {
		return "kinetics";
	}

	private static class Comments {
		static String fanPushDistance = "Maximum distance in blocks Fans can push entities.";
		static String fanPullDistance = "Maximum distance in blocks from where Fans can pull entities.";
		static String fanBlockCheckRate = "Game ticks between Fans checking for anything blocking their air flow.";
		static String fanRotationArgmax = "Rotation speed at which the maximum stats of fans are reached.";
		static String fanProcessingTime = "Game ticks required for a Fan-based processing recipe to take effect.";

		static String stats = "Configure speed/capacity levels for requirements and indicators.";
		static String rpm = "[in Revolutions per Minute]";
		static String stress = "Fine tune the kinetic stats of individual components";
	}

}
