package site.codecrew.world.master.infrastructure;

import java.time.Duration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import site.codecrew.world.master.domain.EnterToken;
import site.codecrew.world.master.domain.WorldEnterTokenCacheRepository;
import tools.jackson.databind.ObjectMapper;

@Repository
class RedisWorldEnterTokenCacheRepository extends AbstractRedisRepository<EnterToken> implements WorldEnterTokenCacheRepository {

    protected RedisWorldEnterTokenCacheRepository(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        super(redisTemplate, objectMapper, EnterToken.class);
    }

    @Override
    protected Duration getTtl() {
        return Duration.ofSeconds(30);
    }

    @Override
    public void save(EnterToken enterToken) {
        set(enterToken.rawToken(), enterToken);
    }
}
