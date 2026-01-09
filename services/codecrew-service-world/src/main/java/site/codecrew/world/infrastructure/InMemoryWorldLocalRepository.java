package site.codecrew.world.infrastructure;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.World;
import site.codecrew.world.domain.WorldLocalRepository;
import site.codecrew.world.domain.exception.WorldFullException;

@Repository
class InMemoryWorldLocalRepository extends SimpleMemoryLocalRepository<World, Long> implements WorldLocalRepository {


    protected InMemoryWorldLocalRepository() {
        super(new ConcurrentHashMap<>(), World::getId);
    }

    @Override
    public Mono<Boolean> tryOccupy(Long worldId) {
        return super.doAtomicUpdate(
                worldId,
                world -> world.getCurrentCapacity() < world.getMaxCapacity(),
                World::increateCurrentPlayer
            )
            .filter(Boolean::booleanValue)
            .switchIfEmpty(Mono.error(new WorldFullException(worldId)));
    }

    @Override
    public Mono<Void> deleteById(Long worldId) {
        return super.doAtomicUpdate(
            worldId,
            world -> world.getCurrentCapacity() > 0,
            World::decrementCurrentPlayer
        ).then();
    }
}
