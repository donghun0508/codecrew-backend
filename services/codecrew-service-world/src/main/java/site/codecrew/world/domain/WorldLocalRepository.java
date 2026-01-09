package site.codecrew.world.domain;

import reactor.core.publisher.Mono;

public interface WorldLocalRepository extends LocalRepository<World, Long> {
    Mono<Boolean> tryOccupy(Long worldId);
}