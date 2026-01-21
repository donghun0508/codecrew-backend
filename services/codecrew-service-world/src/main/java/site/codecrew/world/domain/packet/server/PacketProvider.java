package site.codecrew.world.domain.packet.server;

import reactor.core.publisher.Mono;
import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.packet.Packet;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.Payload;

public interface PacketProvider {

    PacketType packetType();
    Mono<Packet<? extends Payload>> create(Connection connection);
}
