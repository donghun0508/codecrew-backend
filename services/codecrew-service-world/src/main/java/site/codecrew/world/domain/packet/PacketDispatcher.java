package site.codecrew.world.domain.packet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.connection.ConnectionService;
import site.codecrew.world.domain.connection.WorldNodeId;
import site.codecrew.world.domain.packet.transmission.Broadcast;
import site.codecrew.world.domain.packet.transmission.Multicast;
import site.codecrew.world.domain.packet.transmission.TransmissionOption;
import site.codecrew.world.domain.packet.transmission.Unicast;
import tools.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
@Component
public class PacketDispatcher {

    private final ConnectionService connectionService;
    private final ObjectMapper objectMapper;

    public Mono<Void> dispatch(Connection connection, Packet<? extends Payload> packet) {
        Payload payload = packet.payload();

        if (payload instanceof Unicast) {
            return sendToOne(connection, packet);
        } else if (payload instanceof Multicast) {
            return sendToMany(connection, packet);
        }
        return Mono.empty();
    }

    private Mono<Void> sendToOne(Connection connection, Packet<?> packet) {
        return Mono.fromCallable(() -> objectMapper.writeValueAsString(packet)).flatMap(connection::send);
    }

    private Mono<Void> sendToMany(Connection sender, Packet<?> packet) {
        Multicast multicast = (Multicast) packet.payload();
        boolean excludeSender = multicast.options().contains(TransmissionOption.EXCLUDE_SENDER);

        return Mono.fromCallable(() -> objectMapper.writeValueAsString(packet))
            .flatMap(json -> connectionService.findAllByNodeId(new WorldNodeId(sender.worldId()))
                .filter(conn -> !excludeSender || !conn.equals(sender))
                .flatMap(conn -> conn.send(json))
                .then()
            );
    }
}