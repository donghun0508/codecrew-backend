package site.codecrew.world.domain.packet.server.payload;

import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.packet.Packet;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.Payload;
import site.codecrew.world.domain.packet.transmission.Multicast;

public record WorldPlayerMissionDeletedPayload(
    String playerId,
    long missionId
) implements Payload, Multicast {

    public static Packet<WorldPlayerMissionDeletedPayload> toPacket(
        Connection connection, long missionId
    ) {
        return Packet.of(PacketType.C_NET_MISSION_DELETED_NOTIFY,
            connection.getId(),
            new WorldPlayerMissionDeletedPayload(connection.getIdentityHash(), missionId));
    }
}
