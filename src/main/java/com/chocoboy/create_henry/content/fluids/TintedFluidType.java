package com.chocoboy.create_henry.content.fluids;

import org.joml.Vector3f;

import net.minecraft.resources.ResourceLocation;

/**
 * Client-side render configuration for a Henry fluid.
 *
 * <p>On Forge this data lived on a {@code FluidType} client extension. Porting Lib's
 * {@code FluidType} carries no client extensions, so the texture, tint and fog data is held
 * here and consumed by the client entrypoint, which registers it with the Fabric fluid render
 * handlers.
 */
public abstract class TintedFluidType {

	protected static final int NO_TINT = 0xffffffff;

	private final ResourceLocation stillTexture;
	private final ResourceLocation flowingTexture;

	protected TintedFluidType(ResourceLocation stillTexture, ResourceLocation flowingTexture) {
		this.stillTexture = stillTexture;
		this.flowingTexture = flowingTexture;
	}

	public ResourceLocation getStillTexture() {
		return stillTexture;
	}

	public ResourceLocation getFlowingTexture() {
		return flowingTexture;
	}

	/**
	 * Tint applied to the fluid block/sprite. Alpha is significant: an alpha of {@code 0}
	 * stops Optifine from forcing biome colours onto the fluid.
	 */
	public abstract int getTintColor();

	protected Vector3f getCustomFogColor() {
		return null;
	}

	protected float getFogDistanceModifier() {
		return 1f;
	}

}
