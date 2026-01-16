package site.codecrew.world.domain.packet.server.payload;

import site.codecrew.world.domain.packet.Packet;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.Payload;
import site.codecrew.world.domain.packet.transmission.Multicast;

public record WorldPlayerLeavePayload(
    String playerId
) implements Payload, Multicast {

    public static Packet<WorldPlayerLeavePayload> toPacket(String playerId) {
        return Packet.of(PacketType.C_NET_PLAYER_LEFT_NOTIFY, new WorldPlayerLeavePayload(playerId));
    }
}
