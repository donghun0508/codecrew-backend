package site.codecrew.world.infrastructure;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.World;
import site.codecrew.world.domain.repository.WorldLocalRepository;

@Repository
public class InMemoryWorldLocalRepository extends SimpleMemoryRepository<World, Long>
    implements WorldLocalRepository {

    public InMemoryWorldLocalRepository() {
        super(new ConcurrentHashMap<>(), World::getId);
    }

    @Override
    public Mono<Integer> tryOccupy(Long worldId) {
        return super.doAtomicUpdate(
                worldId,
                world -> world.getCurrentCapacity() < world.getMaxCapacity(),
                World::increateCurrentPlayer
            )
            .filter(isUpdated -> isUpdated) // 1. 업데이트 성공(true)한 경우만 통과
            .flatMap(isUpdated -> super.findById(worldId)) // 2. 변경된 월드 정보를 다시 조회
            .map(world -> world.getMaxCapacity() - world.getCurrentCapacity()) // 3. 남은 자리 계산 (Max - Current)
            .switchIfEmpty(Mono.error(new IllegalStateException("World is full"))); // 4. 실패(false) 시 에러 반환
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