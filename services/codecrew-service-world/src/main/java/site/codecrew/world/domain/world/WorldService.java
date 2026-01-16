package site.codecrew.world.domain.world;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorldService {

    private final WorldRepository worldRepository;
    private final WorldLocalRepository worldLocalRepository;

    public Mono<Void> ensureCapacityAvailable(Long worldId) {
        return worldLocalRepository.findById(worldId)
            .doOnNext(World::assertCapacityAvailable).then();
    }

    public Mono<Void> tryOccupy(Long worldId) {
        return worldLocalRepository.findById(worldId)
            .switchIfEmpty(
                worldRepository.findById(worldId)
                    .switchIfEmpty(Mono.error(new CoreException(CoreErrorCode.NOT_FOUND, "World not found", worldId)))
                    .delayUntil(worldLocalRepository::save)
            )
            .doOnNext(World::occupy)
            .doOnSuccess(world -> log.debug("[World] Occupied: {}", world))
            .then();
    }

    public Mono<World> fetchLocalById(long worldId) {
        return worldLocalRepository.findById(worldId)
            .switchIfEmpty(Mono.error(new CoreException(CoreErrorCode.NOT_FOUND, "World must be loaded in memory to vacate", worldId)));
    }

    public Mono<Void> vacate(Long worldId) {
        return fetchLocalById(worldId)
            .doOnNext(World::vacate)
            .doOnSuccess(world -> log.debug("[World] Vacated: {}", world))
            .then();
    }
}
