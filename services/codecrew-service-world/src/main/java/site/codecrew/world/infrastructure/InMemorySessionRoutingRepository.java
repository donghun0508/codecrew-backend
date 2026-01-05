package site.codecrew.world.infrastructure;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.SessionRouting;
import site.codecrew.world.domain.repository.SessionRoutingRepository;

@Repository
public class InMemorySessionRoutingRepository extends SimpleMemoryRepository<SessionRouting, String>
    implements SessionRoutingRepository {

    private final ConcurrentHashMap<Long, Set<String>> routingIndex = new ConcurrentHashMap<>();

    public InMemorySessionRoutingRepository() {
        super(new ConcurrentHashMap<>(), SessionRouting::sessionId);
    }

    @Override
    public Mono<SessionRouting> save(SessionRouting entity) {
        return super.findById(entity.sessionId())
            .doOnNext(oldEntity -> {
                if (oldEntity.routingKey() != entity.routingKey()) {
                    removeFromIndex(oldEntity.routingKey(), oldEntity.sessionId());
                }
            })
            .then(super.save(entity))
            .doOnNext(saved -> {
                addToIndex(saved.routingKey(), saved.sessionId());
            });
    }

    @Override
    public Mono<Void> deleteById(String sessionId) {
        return super.findById(sessionId)
            .flatMap(entity -> {
                removeFromIndex(entity.routingKey(), sessionId);
                return super.deleteById(sessionId);
            });
    }

    @Override
    public Flux<String> findSessionIdsByRoutingKey(long routingKey) {
        Set<String> sessions = routingIndex.getOrDefault(routingKey, Collections.emptySet());
        return Flux.fromIterable(sessions);
    }

    private void addToIndex(long routingKey, String sessionId) {
        routingIndex.computeIfAbsent(routingKey, k -> ConcurrentHashMap.newKeySet())
            .add(sessionId);
    }

    private void removeFromIndex(long routingKey, String sessionId) {
        routingIndex.computeIfPresent(routingKey, (key, sessions) -> {
            sessions.remove(sessionId);
            return sessions.isEmpty() ? null : sessions;
        });
    }
}