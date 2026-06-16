package com.chocoboy.create_henry.infrastructure.network;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import com.simibubi.create.foundation.networking.SimplePacketBase.NetworkDirection;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import com.chocoboy.create_henry.HenryCreate;

import java.util.function.Function;

import static com.simibubi.create.foundation.networking.SimplePacketBase.NetworkDirection.PLAY_TO_SERVER;

public enum HenryPackets {

	// Client to Server
	OBSERVER_GAUGEOMETER(GaugeObservedPacket.class, GaugeObservedPacket::new, PLAY_TO_SERVER),
	;

	public static final ResourceLocation CHANNEL_NAME = HenryCreate.asResource("main");

	private static SimpleChannel channel;

	private final PacketType<?> packetType;

	<T extends SimplePacketBase> HenryPackets(Class<T> type, Function<FriendlyByteBuf, T> factory,
		NetworkDirection direction) {
		packetType = new PacketType<>(type, factory, direction);
	}

	public static void registerPackets() {
		channel = new SimpleChannel(CHANNEL_NAME);

		for (HenryPackets packet : values())
			packet.packetType.register();

		channel.initServerListener();
	}

	public static SimpleChannel getChannel() {
		return channel;
	}

	public static void sendToServer(SimplePacketBase packet) {
		getChannel().sendToServer(packet);
	}

	private static class PacketType<T extends SimplePacketBase> {
		private static int index = 0;

		private final Function<FriendlyByteBuf, T> decoder;
		private final Class<T> type;
		private final NetworkDirection direction;

		private PacketType(Class<T> type, Function<FriendlyByteBuf, T> factory, NetworkDirection direction) {
			this.decoder = factory;
			this.type = type;
			this.direction = direction;
		}

		private void register() {
			switch (direction) {
				case PLAY_TO_CLIENT -> getChannel().registerS2CPacket(type, index++, decoder);
				case PLAY_TO_SERVER -> getChannel().registerC2SPacket(type, index++, decoder);
			}
		}
	}

}
