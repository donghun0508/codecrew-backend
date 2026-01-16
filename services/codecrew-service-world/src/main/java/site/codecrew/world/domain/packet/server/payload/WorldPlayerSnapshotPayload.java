package site.codecrew.world.domain.packet.server.payload;

import java.util.List;
import site.codecrew.world.domain.packet.Packet;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.Payload;
import site.codecrew.world.domain.packet.model.CollectionModel;
import site.codecrew.world.domain.packet.model.PlayerModel;
import site.codecrew.world.domain.player.PlayerWithMission;
import site.codecrew.world.domain.packet.model.SystemModel;
import site.codecrew.world.domain.packet.model.WorldModel;
import site.codecrew.world.domain.packet.transmission.Unicast;
import site.codecrew.world.domain.system.Announcement;
import site.codecrew.world.domain.world.World;

public record WorldPlayerSnapshotPayload(
    String playerId,
    WorldModel world,
    CollectionModel<PlayerModel> players,
    SystemModel system
) implements Payload, Unicast {

    public static Packet<WorldPlayerSnapshotPayload> toPacket(
        String sessionId,
        String currentPlayerId,
        World world,
        List<PlayerWithMission> playerWithMissions,
        Announcement announcement
    ) {
        WorldPlayerSnapshotPayload payload = new WorldPlayerSnapshotPayload(
            currentPlayerId,
            WorldModel.from(world),
            CollectionModel.from(playerWithMissions.stream()
                .map(PlayerModel::from)
                .toList()),
            SystemModel.from(announcement)
        );
        return Packet.of(PacketType.C_NET_WORLD_ENTER_RES, sessionId, payload);
    }
}
