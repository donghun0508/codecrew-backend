package site.codecrew.world.infrastructure;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.PlayerConnection;
import site.codecrew.world.domain.repository.PlayerConnectionRepository;

@Slf4j
@Repository
public class InMemoryPlayerConnectionRepository extends SimpleMemoryRepository<PlayerConnection, String> implements PlayerConnectionRepository {

    private final Map<String, String> playerIdIndex = new ConcurrentHashMap<>();

    public InMemoryPlayerConnectionRepository() {
        super(new ConcurrentHashMap<>(), PlayerConnection::sessionId);
    }

    @Override
    public Mono<PlayerConnection> save(PlayerConnection entity) {
        return super.save(entity)
            .map(saved -> {
                playerIdIndex.put(saved.playerId(), saved.sessionId());
                return saved;
            });
    }

    @Override
    public Mono<Void> deleteById(String sessionId) {
        return findById(sessionId)
            .flatMap(connection -> {
                playerIdIndex.remove(connection.playerId());
                return super.deleteById(sessionId);
            })
            .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Boolean> existsByPlayerId(String playerId) {
        return Mono.fromCallable(() -> {
            return playerIdIndex.containsKey(playerId);
        });
    }
}