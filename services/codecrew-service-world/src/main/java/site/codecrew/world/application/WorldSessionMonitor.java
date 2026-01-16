package site.codecrew.world.application;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.connection.Connection;

@Slf4j
@RequiredArgsConstructor
@Component
public class WorldSessionMonitor {

    private static final Duration PING_INTERVAL = Duration.ofSeconds(10);
    private static final long TIMEOUT_MILLIS = 30_000;
    private static final int MAX_JITTER_SECONDS = 5;
    private static final byte[] EMPTY_PAYLOAD = new byte[0];

    public Mono<Void> monitor(Connection connection) {
        connection.updateHeartbeat();

        long initialDelay = ThreadLocalRandom.current().nextLong(MAX_JITTER_SECONDS * 1000);

        return Mono.delay(Duration.ofMillis(initialDelay))
            .thenMany(Flux.interval(PING_INTERVAL))
            .concatMap(tick -> executeHeartbeat(connection))
            .then();
    }

    private Mono<Void> executeHeartbeat(Connection connection) {
        if (!connection.getSocket().isOpen()) {
            return Mono.empty();
        }

        if (System.currentTimeMillis() - connection.getLastHeartbeat() > TIMEOUT_MILLIS) {
            return Mono.error(new TimeoutException("Session Heartbeat Timeout"));
        }

        WebSocketMessage pingMessage = connection.getSocket().pingMessage(factory -> factory.wrap(EMPTY_PAYLOAD));
        return connection.getSocket().send(Mono.just(pingMessage));
    }
}