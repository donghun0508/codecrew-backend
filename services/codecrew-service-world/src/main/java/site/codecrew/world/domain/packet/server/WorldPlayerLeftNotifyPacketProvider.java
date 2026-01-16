package site.codecrew.world.domain.packet.server;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.packet.Packet;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.server.payload.WorldPlayerLeavePayload;

@RequiredArgsConstructor
@Component
class WorldPlayerLeftNotifyPacketProvider implements PacketProvider {

    @Override
    public PacketType packetType() {
        return PacketType.C_NET_PLAYER_LEFT_NOTIFY;
    }

    @Override
    public Mono<Packet<?>> create(Connection connection) {
        return Mono.just(WorldPlayerLeavePayload.toPacket(connection.getIdentityHash()));
    }
}
