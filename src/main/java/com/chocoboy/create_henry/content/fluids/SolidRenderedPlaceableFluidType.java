package com.chocoboy.create_henry.content.fluids;

import java.util.function.Supplier;

import org.joml.Vector3f;

import net.createmod.catnip.utility.theme.Color;
import net.minecraft.resources.ResourceLocation;

public class SolidRenderedPlaceableFluidType extends TintedFluidType {

	private Supplier<Vector3f> fogColor;
	private Supplier<Float> fogDistance;
	private int tintRGB = -1; // -1 means no tint

	public static SolidRenderedPlaceableFluidType create(int fogColor, Supplier<Float> fogDistance,
			ResourceLocation stillTex, ResourceLocation flowTex) {
		SolidRenderedPlaceableFluidType fluidType = new SolidRenderedPlaceableFluidType(stillTex, flowTex);
		Vector3f baked = new Color(fogColor, false).asVectorF();
		fluidType.fogColor = () -> baked;
		fluidType.fogDistance = fogDistance;
		return fluidType;
	}

	public static SolidRenderedPlaceableFluidType create(Supplier<Integer> fogColor, Supplier<Float> fogDistance,
			ResourceLocation stillTex, ResourceLocation flowTex) {
		SolidRenderedPlaceableFluidType fluidType = new SolidRenderedPlaceableFluidType(stillTex, flowTex);
		fluidType.fogColor = () -> new Color(fogColor.get(), false).asVectorF();
		fluidType.fogDistance = fogDistance;
		return fluidType;
	}

	public static SolidRenderedPlaceableFluidType createTinted(int tintColor, Supplier<Float> fogDistance,
			ResourceLocation stillTex, ResourceLocation flowTex) {
		SolidRenderedPlaceableFluidType fluidType = new SolidRenderedPlaceableFluidType(stillTex, flowTex);
		Vector3f baked = new Color(tintColor, false).asVectorF();
		fluidType.fogColor = () -> baked;
		fluidType.fogDistance = fogDistance;
		fluidType.tintRGB = tintColor & 0x00FFFFFF;
		return fluidType;
	}

	private SolidRenderedPlaceableFluidType(ResourceLocation stillTexture, ResourceLocation flowingTexture) {
		super(stillTexture, flowingTexture);
	}

	/*
	 * Removing alpha from the tint prevents Optifine from forcibly applying biome colors to
	 * modded fluids (this workaround only works for fluids in the solid render layer).
	 */
	@Override
	public int getTintColor() {
		// alpha=0 prevents optifine from applying biome colors; RGB provides the tint
		return tintRGB == -1 ? 0x00FFFFFF : tintRGB;
	}

	@Override
	public Vector3f getCustomFogColor() {
		return fogColor.get();
	}

	@Override
	public float getFogDistanceModifier() {
		return fogDistance.get();
	}

}
