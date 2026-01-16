package site.codecrew.world.domain.packet.server.payload;

import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.packet.Packet;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.Payload;
import site.codecrew.world.domain.packet.model.MissionModel;
import site.codecrew.world.domain.packet.transmission.Multicast;
import site.codecrew.world.domain.player.Mission;

public record WorldPlayerMissionRegisteredPayload(
    String playerId,
    MissionModel mission
) implements Payload, Multicast {

    public static Packet<WorldPlayerMissionRegisteredPayload> toPacket(
        Connection connection, Mission mission
    ) {
        return Packet.of(PacketType.C_NET_MISSION_REGISTERED_NOTIFY,
            connection.getId(),
            new WorldPlayerMissionRegisteredPayload(connection.getIdentityHash(), MissionModel.from(mission)));
    }
}
