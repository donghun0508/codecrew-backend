package site.codecrew.world.domain.packet.server.payload;

import java.util.List;
import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.packet.Packet;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.Payload;
import site.codecrew.world.domain.packet.model.CollectionModel;
import site.codecrew.world.domain.packet.transmission.Multicast;

public record WorldPlayerMissionDeleteAllPayload(
    String playerId,
    List<Long> deletedMissionIds
) implements Payload, Multicast {

    public static Packet<WorldPlayerMissionDeleteAllPayload> toPacket(
        Connection connection, List<Long> deletedMissionIds
    ) {
        return Packet.of(PacketType.C_NET_MISSION_DELETED_ALL_NOTIFY,
            connection.getId(),
            new WorldPlayerMissionDeleteAllPayload(connection.getIdentityHash(), deletedMissionIds));
    }
}
