package site.codecrew.world.domain.packet.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.packet.PacketDispatcher;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.client.payload.MissionDeleteClientPayload;
import site.codecrew.world.domain.packet.server.payload.WorldPlayerMissionDeletedPayload;
import site.codecrew.world.domain.player.MissionService;

@RequiredArgsConstructor
@Component
class MissionDeleteHandler implements InboundPacketHandler<MissionDeleteClientPayload> {

    private final MissionService missionService;
    private final PacketDispatcher packetDispatcher;

    @Override
    public PacketType packetType() {
        return PacketType.C_NET_MISSION_DELETE_REQ;
    }

    @Override
    public Mono<Void> handle(Connection connection, MissionDeleteClientPayload payload) {
        return missionService.deleteById(payload.missionId())
            .then(Mono.fromCallable(() -> WorldPlayerMissionDeletedPayload.toPacket(connection, payload.missionId())))
            .flatMap(packet -> packetDispatcher.dispatch(connection, packet));
    }
}
