package site.codecrew.world.domain.repository;

import reactor.core.publisher.Flux;
import site.codecrew.world.domain.SessionRouting;

public interface SessionRoutingRepository extends Repository<SessionRouting, String> {
    Flux<String> findSessionIdsByRoutingKey(long routingKey);
}