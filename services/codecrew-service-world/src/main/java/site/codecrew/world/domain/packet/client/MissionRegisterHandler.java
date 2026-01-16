package site.codecrew.world.domain.packet.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.packet.PacketDispatcher;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.client.payload.MissionRegisterClientPayload;
import site.codecrew.world.domain.packet.server.payload.WorldPlayerMissionRegisteredPayload;
import site.codecrew.world.domain.player.Mission;
import site.codecrew.world.domain.player.MissionService;

@RequiredArgsConstructor
@Component
class MissionRegisterHandler implements InboundPacketHandler<MissionRegisterClientPayload> {

    private final MissionService missionService;
    private final PacketDispatcher packetDispatcher;

    @Override
    public PacketType packetType() {
        return PacketType.C_NET_MISSION_REGISTER_REQ;
    }

    @Override
    public Mono<Void> handle(Connection connection, MissionRegisterClientPayload payload) {
        Mission mission = Mission.create(
            connection.getPlayerId(),
            payload.title(),
            payload.description(),
            payload.githubUrl()
        );

        return missionService.save(mission)
            .map(saved -> WorldPlayerMissionRegisteredPayload.toPacket(connection, saved))
            .flatMap(packet -> packetDispatcher.dispatch(connection, packet));
    }
}
