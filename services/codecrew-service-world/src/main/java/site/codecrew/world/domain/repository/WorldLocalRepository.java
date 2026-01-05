package site.codecrew.world.domain.repository;

import reactor.core.publisher.Mono;
import site.codecrew.world.domain.World;

public interface WorldLocalRepository extends Repository<World, Long> {
    Mono<Integer> tryOccupy(Long worldId);
}