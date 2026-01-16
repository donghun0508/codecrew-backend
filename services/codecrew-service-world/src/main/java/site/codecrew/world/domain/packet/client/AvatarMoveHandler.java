package site.codecrew.world.domain.packet.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.packet.PacketDispatcher;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.client.payload.AvatarMoveClientPayload;
import site.codecrew.world.domain.packet.server.payload.AvatarMovedPayload;
import site.codecrew.world.domain.player.PlayerService;

@RequiredArgsConstructor
@Component
class AvatarMoveHandler implements InboundPacketHandler<AvatarMoveClientPayload> {

    private final PlayerService playerService;
    private final PacketDispatcher packetDispatcher;

    @Override
    public PacketType packetType() {
        return PacketType.C_NET_AVATAR_MOVE_REQ;
    }

    @Override
    public Mono<Void> handle(Connection connection, AvatarMoveClientPayload payload) {
        return playerService.fetchLocalById(connection.getIdentityHash())
            .doOnNext(player -> player.updateCoordinate(payload.x(), payload.y()))
            .map(player -> AvatarMovedPayload.toPacket(connection, payload))
            .flatMap(packet -> packetDispatcher.dispatch(connection, packet));
    }
}
