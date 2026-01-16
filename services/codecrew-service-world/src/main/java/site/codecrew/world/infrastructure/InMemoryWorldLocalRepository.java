package site.codecrew.world.infrastructure;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import site.codecrew.world.domain.world.World;
import site.codecrew.world.domain.world.WorldLocalRepository;

@Repository
class InMemoryWorldLocalRepository extends SimpleMemoryLocalRepository<World, Long> implements WorldLocalRepository {

    protected InMemoryWorldLocalRepository() {
        super(new ConcurrentHashMap<>(), World::getId);
    }
}
