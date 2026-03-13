package com.chocoboy.create_henry.content.fluids;

import java.util.function.Supplier;

import org.joml.Vector3f;

import net.createmod.catnip.theme.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidStack;
import com.tterrag.registrate.builders.FluidBuilder.FluidTypeFactory;

public class SolidRenderedPlaceableFluidType extends TintedFluidType {

	private Vector3f fogColor;
	private Supplier<Float> fogDistance;

	public static FluidTypeFactory create(int fogColor, Supplier<Float> fogDistance) {
		return (p, s, f) -> {
			SolidRenderedPlaceableFluidType fluidType = new SolidRenderedPlaceableFluidType(p, s, f);
			fluidType.fogColor = new Color(fogColor, false).asVectorF();
			fluidType.fogDistance = fogDistance;
			return fluidType;
		};
	}

	private SolidRenderedPlaceableFluidType(Properties properties, ResourceLocation stillTexture,
											ResourceLocation flowingTexture) {
		super(properties, stillTexture, flowingTexture);
	}

	@Override
	protected int getTintColor(FluidStack stack) {
		return NO_TINT;
	}

	/*
	 * Removing alpha from tint prevents optifine from forcibly applying biome
	 * colors to modded fluids (this workaround only works for fluids in the solid
	 * render layer)
	 */
	@Override
	public int getTintColor(FluidState state, BlockAndTintGetter world, BlockPos pos) {
		return 0x00ffffff;
	}

	@Override
	protected Vector3f getCustomFogColor() {
		return fogColor;
	}

	@Override
	protected float getFogDistanceModifier() {
		return fogDistance.get();
	}

}
