package site.codecrew.world.domain.packet.server.payload;

import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.packet.Packet;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.Payload;
import site.codecrew.world.domain.packet.model.PlayerSimpleModel;
import site.codecrew.world.domain.packet.transmission.Multicast;
import site.codecrew.world.domain.player.Player;

public record ChatMessagePayload(
    String message,
    PlayerSimpleModel player
) implements Payload, Multicast {

    public static Packet<ChatMessagePayload> toPacket(Connection connection, String message, Player player) {
        return Packet.of(PacketType.C_NET_CHAT_MESSAGE_NOTIFY,
            connection.getId(),
            new ChatMessagePayload(message, PlayerSimpleModel.from(player))
        );
    }
}
