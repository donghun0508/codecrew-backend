package site.codecrew.world.master.infrastructure;

import java.time.Duration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import site.codecrew.world.master.domain.Avatar;
import site.codecrew.world.master.domain.AvatarCacheRepository;
import site.codecrew.world.master.domain.PlayerId;
import tools.jackson.databind.ObjectMapper;

@Repository
class RedisAvatarCacheRepository extends AbstractRedisRepository<Avatar> implements AvatarCacheRepository {

    protected RedisAvatarCacheRepository(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        super(redisTemplate, objectMapper, Avatar.class);
    }

    @Override
    public boolean existsByWorldIdAndPlayerId(Long worldId, PlayerId playerId) {
        String compositeKey = generateCompositeKey(worldId, playerId);
        return exists(compositeKey);
    }

    @Override
    protected Duration getTtl() {
        return Duration.ofHours(1);
    }

    @Override
    public void save(Avatar avatar) {
        set(generateCompositeKey(avatar), avatar);
    }

    private String generateCompositeKey(Avatar avatar) {
        return generateCompositeKey(avatar.getWorldId(), avatar.getPlayerId());
    }

    private String generateCompositeKey(Long worldId, PlayerId playerId) {
        return "world:" + worldId + ":players:" + playerId.value();
    }
}

