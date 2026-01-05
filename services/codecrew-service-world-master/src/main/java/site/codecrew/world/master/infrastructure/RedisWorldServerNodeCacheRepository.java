package site.codecrew.world.master.infrastructure;

import java.time.Duration;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import site.codecrew.world.master.domain.ServerNode;
import site.codecrew.world.master.domain.WorldServerNodeCacheRepository;
import tools.jackson.databind.ObjectMapper;

@Repository
public class RedisWorldServerNodeCacheRepository
    extends AbstractRedisRepository<ServerNode, RedisWorldServerNodeCacheRepository.ServerNodeCacheDto>
    implements WorldServerNodeCacheRepository {

    @Value("${app.world.cache.server-heartbeat}")
    private Duration serverHeartbeatTtl;

    protected RedisWorldServerNodeCacheRepository(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        super(redisTemplate, objectMapper, ServerNodeCacheDto.class);
    }

    @Override
    protected ServerNodeCacheDto toCacheModel(ServerNode serverNode) {
        return ServerNodeCacheDto.from(serverNode);
    }

    @Override
    protected ServerNode toDomainModel(ServerNodeCacheDto dto) {
        return dto.toDomain();
    }

    @Override
    protected Duration getTtl() {
        return serverHeartbeatTtl;
    }

    @Override
    public Optional<ServerNode> findByWorldId(long worldId) {
        return find(generateKey(worldId));
    }

    @Override
    public void save(long worldId, ServerNode serverNode) {
        String key = generateKey(worldId);
        set(key, serverNode);
    }

    private String generateKey(long worldId) {
        return "world:" + worldId + ":node";
    }

    protected record ServerNodeCacheDto(
        String id,
        String ip
    ) {
        public static ServerNodeCacheDto from(ServerNode node) {
            return new ServerNodeCacheDto(node.id(), node.host());
        }

        public ServerNode toDomain() {
            return new ServerNode(id, ip);
        }
    }
}
