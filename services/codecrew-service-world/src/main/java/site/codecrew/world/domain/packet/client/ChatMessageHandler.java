package site.codecrew.world.domain.packet.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.packet.PacketDispatcher;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.client.payload.ChatMessageClientPayload;
import site.codecrew.world.domain.packet.server.payload.ChatMessagePayload;
import site.codecrew.world.domain.player.PlayerService;

@RequiredArgsConstructor
@Component
class ChatMessageHandler implements InboundPacketHandler<ChatMessageClientPayload> {

    private final PlayerService playerService;
    private final PacketDispatcher packetDispatcher;

    @Override
    public PacketType packetType() {
        return PacketType.C_NET_CHAT_SEND_REQ;
    }

    @Override
    public Mono<Void> handle(Connection connection, ChatMessageClientPayload payload) {
        return playerService.fetchLocalById(connection.getIdentityHash())
            .map(player -> ChatMessagePayload.toPacket(connection, payload.message(), player))
            .flatMap(packet -> packetDispatcher.dispatch(connection, packet));
    }
}