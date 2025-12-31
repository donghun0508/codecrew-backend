package site.codecrew.world.master.domain.node;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.codecrew.world.master.domain.ServerNode;

@Slf4j
@Component
@RequiredArgsConstructor
public class WorldServerLoadStore {

    private final WorldServerCapacityRepository worldServerCapacityRepository;

    @Value("${world.instance.max-capacity:300}")
    private int maxInstanceCapacity;

    public Optional<ServerNode> tryReuseServer(int requiredCapacity) {
        // 1. [검색 조건] double 변환 제거 -> int로 깔끔하게 계산
        int limitCount = maxInstanceCapacity - requiredCapacity;

        // Repository 인터페이스도 이제 int를 받으므로 바로 전달
        Optional<ServerNode> candidateOpt = worldServerCapacityRepository.findCandidateNode(limitCount);

        if (candidateOpt.isEmpty()) {
            return Optional.empty();
        }

        ServerNode candidate = candidateOpt.get();

        // 2. [선점] 반환값도 이제 Double(객체)이 아니라 int(기본형)임
        int currentScore = worldServerCapacityRepository.increaseLoad(candidate, requiredCapacity);

        // 3. [검증] null 체크 불필요 (int니까), 바로 크기 비교
        if (currentScore > maxInstanceCapacity) {
            log.warn("Capacity exceeded for node {}. Rollback initiated.", candidate.id());

            // [롤백] 실패했으니 다시 깎음
            worldServerCapacityRepository.increaseLoad(candidate, -requiredCapacity);
            return Optional.empty();
        }

        return Optional.of(candidate);
    }

    public void registerNewNode(ServerNode serverNode, int capacity) {
        worldServerCapacityRepository.registerNewNode(serverNode, capacity);
        log.info("Registered new node: {}", serverNode.id());
    }
}