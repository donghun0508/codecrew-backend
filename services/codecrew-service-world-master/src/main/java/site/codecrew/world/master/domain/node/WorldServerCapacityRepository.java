package site.codecrew.world.master.domain.node;

import java.util.Optional;
import site.codecrew.world.master.domain.ServerNode;

public interface WorldServerCapacityRepository {

    // 인자: double -> int 변경 (limitScore -> limitCount)
    Optional<ServerNode> findCandidateNode(int limitCount);

    // 반환타입: Double -> int 변경 (반환값은 갱신된 총 인원수)
    int increaseLoad(ServerNode serverNode, int amount);

    void registerNewNode(ServerNode serverNode, int initialCapacity);
}