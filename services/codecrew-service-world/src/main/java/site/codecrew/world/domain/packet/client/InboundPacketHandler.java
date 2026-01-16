package site.codecrew.world.domain.packet.client;

import reactor.core.publisher.Mono;
import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.Payload;

public interface InboundPacketHandler<T extends Payload> {
    PacketType packetType();
    Mono<Void> handle(Connection connection, T payload);
}