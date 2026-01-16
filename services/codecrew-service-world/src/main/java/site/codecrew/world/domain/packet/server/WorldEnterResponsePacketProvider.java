package site.codecrew.world.domain.packet.server;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.connection.ConnectionService;
import site.codecrew.world.domain.connection.WorldNodeId;
import site.codecrew.world.domain.packet.Packet;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.server.payload.WorldPlayerSnapshotPayload;
import site.codecrew.world.domain.player.PlayerWithMission;
import site.codecrew.world.domain.player.MissionService;
import site.codecrew.world.domain.player.PlayerService;
import site.codecrew.world.domain.system.AnnouncementService;
import site.codecrew.world.domain.world.WorldService;

@RequiredArgsConstructor
@Component
class WorldEnterResponsePacketProvider implements PacketProvider {

    private final AnnouncementService announcementService;
    private final WorldService worldService;
    private final ConnectionService connectionService;
    private final PlayerService playerService;
    private final MissionService missionService;

    @Override
    public PacketType packetType() {
        return PacketType.C_NET_WORLD_ENTER_RES;
    }

    @Override
    public Mono<Packet<?>> create(Connection connection) {
        return Mono.zip(
                worldService.fetchLocalById(connection.worldId()),
                fetchAllPlayersWithMissions(connection.worldId()),
                announcementService.fetchDefault()
            )
            .map(tuple -> (Packet<?>) WorldPlayerSnapshotPayload.toPacket(
                connection.getId(),
                connection.getIdentityHash(),
                tuple.getT1(),
                tuple.getT2(),
                tuple.getT3()
            ));
    }

    private Mono<List<PlayerWithMission>> fetchAllPlayersWithMissions(long worldId) {
        return connectionService.findAllByNodeId(new WorldNodeId(worldId))
            .flatMap(conn -> playerService.fetchLocalById(conn.getIdentityHash())
                .flatMap(player -> missionService.findAllByPlayerIdToday(player.id())
                    .collectList()
                    .map(missions -> new PlayerWithMission(player, missions))
                )
            )
            .collectList();
    }
}
