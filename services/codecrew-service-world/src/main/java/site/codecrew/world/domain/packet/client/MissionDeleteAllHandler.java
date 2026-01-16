package site.codecrew.world.domain.packet.client;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.packet.PacketDispatcher;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.client.payload.MissionDeleteAllClientPayload;
import site.codecrew.world.domain.packet.server.payload.WorldPlayerMissionDeleteAllPayload;
import site.codecrew.world.domain.player.MissionService;
import site.codecrew.world.domain.player.MissionStatus;
import site.codecrew.world.domain.player.PlayerService;

@RequiredArgsConstructor
@Component
class MissionDeleteAllHandler implements InboundPacketHandler<MissionDeleteAllClientPayload> {

    private final PlayerService playerService;
    private final MissionService missionService;
    private final PacketDispatcher packetDispatcher;

    @Override
    public PacketType packetType() {
        return PacketType.C_NET_MISSION_DELETE_ALL_REQ;
    }

    @Override
    public Mono<Void> handle(Connection connection, MissionDeleteAllClientPayload payload) {
        return playerService.fetchLocalById(connection.getIdentityHash())
            .flatMapMany(player -> missionService.findByPlayerIdAndMissionStatusIn(
                player.id(),
                List.of(MissionStatus.PENDING, MissionStatus.PROCESSING)
            ))
            .collectList()
            .flatMap(missionIds -> missionService.deleteAllByIds(missionIds)
                .thenReturn(missionIds)
            )
            .map(deletedIds -> WorldPlayerMissionDeleteAllPayload.toPacket(connection, deletedIds))
            .flatMap(packet -> packetDispatcher.dispatch(connection, packet));
    }
}