package site.codecrew.world.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;
import site.codecrew.world.domain.exception.WorldFullException;

@RequiredArgsConstructor
@Service
class WorldService {

    private final WorldRepository repository;
    private final WorldLocalRepository localRepository;

    public Mono<World> fetchById(long worldId) {
        return findById(worldId)
            .switchIfEmpty(Mono.error(() -> new CoreException(CoreErrorCode.NOT_FOUND, "World not found", worldId)));
    }

    public Mono<World> findById(long worldId) {
        return repository.findById(worldId);
    }

    public Mono<Void> reserve(World world) {
        return localRepository.findById(world.getId())
            .switchIfEmpty(localRepository.save(world))
            .flatMap(w -> localRepository.tryOccupy(w.getId()))
            .filter(isSuccess -> isSuccess)
            .switchIfEmpty(Mono.error(new WorldFullException(world.getId())))
            .then();
    }

    public Mono<Void> release(World world) {
        return localRepository.deleteById(world.getId());
    }
}
