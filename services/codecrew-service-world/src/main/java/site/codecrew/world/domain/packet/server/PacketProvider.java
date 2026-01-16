package site.codecrew.world.domain.packet.server;

import reactor.core.publisher.Mono;
import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.packet.Packet;
import site.codecrew.world.domain.packet.PacketType;

public interface PacketProvider {

    PacketType packetType();
    Mono<Packet<?>> create(Connection connection);
}
