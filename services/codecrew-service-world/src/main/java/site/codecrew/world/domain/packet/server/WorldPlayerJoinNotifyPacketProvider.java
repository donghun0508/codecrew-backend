package site.codecrew.world.domain.packet.server;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.packet.Packet;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.server.payload.WorldPlayerJoinPayload;
import site.codecrew.world.domain.player.MissionService;
import site.codecrew.world.domain.player.PlayerService;

@RequiredArgsConstructor
@Component
class WorldPlayerJoinNotifyPacketProvider implements PacketProvider {

    private final PlayerService playerService;
    private final MissionService missionService;

    @Override
    public PacketType packetType() {
        return PacketType.C_NET_PLAYER_JOINED_NOTIFY;
    }

    @Override
    public Mono<Packet<?>> create(Connection connection) {

        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();

        return Mono.zip(
                playerService.fetchLocalById(connection.getIdentityHash()),
                missionService.findAllByPlayerIdToday(connection.getPlayerId()).collectList()
            )
            .map(tuple -> (Packet<?>) WorldPlayerJoinPayload.toPacket(
                connection.getId(),
                tuple.getT1(),
                tuple.getT2()
            ));
    }
}
