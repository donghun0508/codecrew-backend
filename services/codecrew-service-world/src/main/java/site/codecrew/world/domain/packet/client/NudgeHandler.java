package site.codecrew.world.domain.packet.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.packet.Packet;
import site.codecrew.world.domain.packet.PacketDispatcher;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.client.payload.NudgeRequestPayload;
import site.codecrew.world.domain.packet.server.payload.NudgePayload;
import site.codecrew.world.domain.player.Player;
import site.codecrew.world.domain.player.PlayerService;

@RequiredArgsConstructor
@Component
class NudgeHandler implements InboundPacketHandler<NudgeRequestPayload> {

    private final PlayerService playerService;
    private final PacketDispatcher packetDispatcher;

    @Override
    public PacketType packetType() {
        return PacketType.C_NET_NUDGE_REQ;
    }

    @Override
    public Mono<Void> handle(Connection connection, NudgeRequestPayload payload) {
        return playerService.fetchLocalById(connection.getIdentityHash())
            .flatMap(player -> {
                Packet<NudgePayload> packet = NudgePayload.toPacket(connection, player);
                return packetDispatcher.unicast(payload.targetPlayerId(), packet);
            });
    }
}
