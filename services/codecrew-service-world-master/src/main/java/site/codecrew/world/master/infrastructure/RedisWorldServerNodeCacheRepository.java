package site.codecrew.world.master.infrastructure;

import java.time.Duration;
import java.util.Optional;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import site.codecrew.world.master.domain.ServerNode;
import site.codecrew.world.master.domain.WorldCode;
import site.codecrew.world.master.domain.WorldServerNodeCacheRepository;
import tools.jackson.databind.ObjectMapper;

@Repository
class RedisWorldServerNodeCacheRepository extends AbstractRedisRepository<ServerNode> implements WorldServerNodeCacheRepository {

    protected RedisWorldServerNodeCacheRepository(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        super(redisTemplate, objectMapper, ServerNode.class);
    }

    @Override
    protected Duration getTtl() {
        // TODO: 이 부분 짧게 가져가야할듯? 이후 월드 서버가 Heartbeat로 계속 연장해야 함
        return Duration.ofHours(10);
    }

    @Override
    public Optional<ServerNode> findByWorldCode(WorldCode worldCode) {
        String key = generateKey(worldCode);
        return find(key);
    }

    @Override
    public void save(WorldCode worldCode, ServerNode serverNode) {
        String key = generateKey(worldCode);
        set(key, serverNode);
    }

    private String generateKey(WorldCode worldCode) {
        return "world:" + worldCode.value() + ":node";
    }
}
