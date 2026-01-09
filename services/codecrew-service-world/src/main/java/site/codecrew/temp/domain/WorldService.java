package site.codecrew.world.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.repository.WorldCacheRepository;
import site.codecrew.world.domain.repository.WorldLocalRepository;
import site.codecrew.world.domain.repository.WorldRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorldService {

    private final WorldRepository worldRepository;
    private final WorldLocalRepository worldLocalRepository;

    public Mono<Void> reserve(long worldId) {
        return worldLocalRepository.existsById(worldId)
            .flatMap(exists -> {
                if (exists) {
                    return Mono.empty();
                }
                return worldRepository.findById(worldId)
                    .flatMap(worldLocalRepository::save);
            })
            .then(worldLocalRepository.tryOccupy(worldId))
            .doOnNext(remaining -> log.info("Reserved worldId: {}, Remaining slots: {}", worldId, remaining))
            .doOnError(e -> log.warn("Failed to reserve worldId: {}, reason: {}", worldId, e.getMessage()))
            .then();
    }

    public Mono<Void> deleteById(Long worldId) {
        return worldLocalRepository.deleteById(worldId)
            .doOnSuccess(unused -> log.info("World capacity deleted for worldId: {}", worldId))
            .then();
    }
}
