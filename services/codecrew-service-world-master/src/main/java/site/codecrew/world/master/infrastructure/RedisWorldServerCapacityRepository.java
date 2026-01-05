package site.codecrew.world.master.infrastructure;

import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;
import site.codecrew.world.master.domain.ServerNode;
import site.codecrew.world.master.domain.node.WorldServerCapacityRepository;

@Repository
@RequiredArgsConstructor
class RedisWorldServerCapacityRepository implements WorldServerCapacityRepository {

    private final StringRedisTemplate redisTemplate;
    private static final String CAPACITY_KEY = "world:nodes:capacity";

    @Override
    public Optional<ServerNode> findCandidateNode(int limitCount) {
        // [변환] int -> double (Redis API 맞춤)
        // 비즈니스 로직(int)을 인프라 로직(double)으로 변환
        double maxScore = (double) limitCount;

        Set<ZSetOperations.TypedTuple<String>> candidates = redisTemplate.opsForZSet()
            .rangeByScoreWithScores(CAPACITY_KEY, 0, maxScore, 0, 1);

        if (candidates == null || candidates.isEmpty()) {
            return Optional.empty();
        }

        String rawValue = candidates.iterator().next().getValue();
        return Optional.of(ServerNode.fromRawString(rawValue));
    }

    @Override
    public int increaseLoad(ServerNode serverNode, int amount) {
        Double resultScore = redisTemplate.opsForZSet()
            .incrementScore(CAPACITY_KEY, serverNode.toRawString(), amount);

        if (resultScore == null) {
            throw new IllegalStateException("Redis command failed");
        }

        return resultScore.intValue();
    }

    @Override
    public void registerNewNode(ServerNode serverNode, int initialCapacity) {
        redisTemplate.opsForZSet()
            .add(CAPACITY_KEY, serverNode.toRawString(), initialCapacity);
    }
}