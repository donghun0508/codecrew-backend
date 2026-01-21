package site.codecrew.world.domain.connection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.DomainEventManaged;
import site.codecrew.world.domain.connection.ConnectionEvent.ConnectionClosedEvent;
import site.codecrew.world.domain.player.Player;
import site.codecrew.world.infrastructure.EventBus;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final EventBus eventBus;

    @DomainEventManaged
    public Mono<Connection> register(WebSocketSession webSocketSession, Player player) {
        Connection connection = Connection.create(webSocketSession, player);
        return connectionRepository.save(connection)
            .doOnSuccess(conn -> log.debug("[Connection] Registered: {}", conn.getIdentityHash()));
    }

    public Mono<Void> delete(Connection connection) {
        return connectionRepository.deleteById(connection.getId())
            .doOnSuccess(v -> {
                eventBus.publish(new ConnectionClosedEvent(connection));
                log.debug("[Connection] Removed: {}", connection.getIdentityHash());
            });
    }

    public Flux<Connection> findAllByNodeId(NodeId nodeId) {
        return connectionRepository.findAllByNode(nodeId);
    }

    public Mono<Connection> findByPlayerId(String playerId) {
        return connectionRepository.findByPlayerId(playerId);
    }
}
