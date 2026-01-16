package site.codecrew.world.application.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.application.ConnectionPacketProcessor;
import site.codecrew.world.domain.connection.ConnectionEvent.ConnectionClosedEvent;
import site.codecrew.world.domain.connection.ConnectionEvent.ConnectionCreatedEvent;

@Slf4j
@RequiredArgsConstructor
@Component
public class ConnectionEventHandler {

    private final ConnectionPacketProcessor connectionPacketProcessor;

    @DomainEventListener
    public Mono<Void> onConnectionCreated(ConnectionCreatedEvent event) {
        log.debug("[Event] ConnectionCreated: {}", event.getConnection());
        return connectionPacketProcessor.connect(event.getConnection());
    }

    @DomainEventListener
    public Mono<Void> onConnectionClosed(ConnectionClosedEvent event) {
        log.debug("[Event] ConnectionClosed: {}", event.getConnection());
        return connectionPacketProcessor.closed(event.getConnection());
    }
}