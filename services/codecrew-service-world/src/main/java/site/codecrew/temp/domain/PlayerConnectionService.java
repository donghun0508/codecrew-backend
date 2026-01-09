package site.codecrew.world.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.repository.PlayerConnectionRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlayerConnectionService {

    private final PlayerConnectionRepository playerSessionRepository;

    public Mono<PlayerConnection> register(PlayerConnection connection) {
        return playerSessionRepository.save(connection);
    }

    public Mono<PlayerConnection> getConnection(String sessionId) {
        return playerSessionRepository.findById(sessionId);
    }

    public Mono<Void> cleanup(String sessionId) {
        return playerSessionRepository.deleteById(sessionId)
            .doOnSuccess(unused -> log.info("Cleanup success for sessionId: {}", sessionId))
            .onErrorResume(e -> Mono.empty());
    }
}
