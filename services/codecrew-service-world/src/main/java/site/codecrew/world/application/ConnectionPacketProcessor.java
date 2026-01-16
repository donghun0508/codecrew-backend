package site.codecrew.world.application;

import static site.codecrew.world.domain.packet.PacketType.C_NET_PLAYER_JOINED_NOTIFY;
import static site.codecrew.world.domain.packet.PacketType.C_NET_PLAYER_LEFT_NOTIFY;
import static site.codecrew.world.domain.packet.PacketType.C_NET_WORLD_ENTER_RES;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.packet.PacketDispatcher;
import site.codecrew.world.domain.packet.factory.PacketFactory;

@RequiredArgsConstructor
@Component
public class ConnectionPacketProcessor {

    private final PacketFactory packetFactory;
    private final PacketDispatcher packetDispatcher;

    public Mono<Void> connect(Connection connection) {
        return packetFactory.create(C_NET_WORLD_ENTER_RES, connection)
            .flatMap(packet -> packetDispatcher.dispatch(connection, packet))
            .then(packetFactory.create(C_NET_PLAYER_JOINED_NOTIFY, connection))
            .flatMap(packet -> packetDispatcher.dispatch(connection, packet));
    }

    public Mono<Void> closed(Connection connection) {
        return packetFactory.create(C_NET_PLAYER_LEFT_NOTIFY, connection)
            .flatMap(packet -> packetDispatcher.dispatch(connection, packet));
    }
}
