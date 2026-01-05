package site.codecrew.world.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.PlayerConnection;
import site.codecrew.world.domain.exception.DuplicateSessionException;
import site.codecrew.world.domain.repository.PlayerConnectionRepository;

@Slf4j
@RequiredArgsConstructor
@Component
public class ConnectionRegisterService {

    private final PlayerConnectionRepository playerConnectionRepository;

    public Mono<Connection> register(Connection connection) {
        String playerId = connection.player().getPublicPlayerId();
        return playerConnectionRepository.existsByPlayerId(playerId)
            .flatMap(exists -> {
                if (exists) {
                    log.warn("Duplicate connection attempt for playerId: {}", playerId);
                    return Mono.error(new DuplicateSessionException(playerId));
                }

                log.info("No duplicate found. Registering new connection for playerId: {}", playerId);
                PlayerConnection newPlayerConnection = new PlayerConnection(
                    connection.worldSession().sessionId(),
                    connection.webSocketSession(),
                    connection.player()
                );

                return playerConnectionRepository.save(newPlayerConnection).thenReturn(connection); // Return the original Connection object for the next step in the chain
            });
    }
}
