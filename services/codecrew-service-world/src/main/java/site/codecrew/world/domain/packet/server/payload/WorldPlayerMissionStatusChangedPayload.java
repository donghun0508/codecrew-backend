package site.codecrew.world.domain.packet.server.payload;

import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.packet.Packet;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.Payload;
import site.codecrew.world.domain.packet.transmission.Multicast;
import site.codecrew.world.domain.player.Mission;
import site.codecrew.world.domain.player.MissionStatus;

public record WorldPlayerMissionStatusChangedPayload(
    long missionId,
    MissionStatus status
) implements Payload, Multicast {

    public static Packet<WorldPlayerMissionStatusChangedPayload> toPacket(Connection connection, Mission mission) {
        return Packet.of(PacketType.C_NET_MISSION_STATUS_CHANGED_NOTIFY,
            connection.getId(),
            new WorldPlayerMissionStatusChangedPayload(mission.getId(), mission.getMissionStatus()));
    }
}
