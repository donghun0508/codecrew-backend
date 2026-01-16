package site.codecrew.world.domain.packet.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.packet.PacketDispatcher;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.client.payload.MissionStatusChangeClientPayload;
import site.codecrew.world.domain.packet.server.payload.WorldPlayerMissionStatusChangedPayload;
import site.codecrew.world.domain.player.MissionService;

@RequiredArgsConstructor
@Component
public class MissionStatusChangeHandler implements InboundPacketHandler<MissionStatusChangeClientPayload> {

    private final MissionService missionService;
    private final PacketDispatcher packetDispatcher;

    @Override
    public PacketType packetType() {
        return PacketType.C_NET_MISSION_STATUS_CHANGE_REQ;
    }

    @Override
    public Mono<Void> handle(Connection connection, MissionStatusChangeClientPayload payload) {
        return missionService.fetchById(payload.missionId())
            .doOnNext(mission -> payload.status().apply(mission))
            .flatMap(missionService::save)
            .map(mission -> WorldPlayerMissionStatusChangedPayload.toPacket(connection, mission))
            .flatMap(packet -> packetDispatcher.dispatch(connection, packet));
    }
}
