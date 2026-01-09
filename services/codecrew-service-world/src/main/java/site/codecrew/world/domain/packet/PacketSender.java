package site.codecrew.world.domain.packet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.packet.payload.Payload;
import site.codecrew.world.domain.routing.MessagePublisherType;
import site.codecrew.world.domain.routing.RoutingService;

@RequiredArgsConstructor
@Component
public class PacketSender {

    private final RoutingService routingService;
    private final PacketSerializer packetSerializer;

    public <T extends Payload> Mono<Void> broadcast(
        String senderSessionId,
        MessagePublisherType type,
        ServerPacket<T> packet
    ) {
        return packetSerializer.serialize(packet)
            .flatMapMany(json ->
                routingService.resolveTargets(senderSessionId, type)
                    .flatMap(session -> session.send(json))
            )
            .then();
    }

    public <T extends Payload> Mono<Void> unicast(
        String targetSessionId,
        ServerPacket<T> packet
    ) {
        return packetSerializer.serialize(packet)
            .flatMap(json ->
                routingService.findSession(targetSessionId)
                    .switchIfEmpty(Mono.error(new IllegalStateException("session not found: " + targetSessionId)))
                    .flatMap(session -> session.send(json))
            );
    }
}
