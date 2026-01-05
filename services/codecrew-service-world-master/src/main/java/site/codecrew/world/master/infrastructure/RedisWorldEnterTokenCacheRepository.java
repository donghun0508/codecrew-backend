package site.codecrew.world.master.infrastructure;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import site.codecrew.world.master.domain.EnterToken;
import site.codecrew.world.master.domain.PlayerId;
import site.codecrew.world.master.domain.WorldCode;
import site.codecrew.world.master.domain.WorldEnterTokenCacheRepository;
import site.codecrew.world.master.domain.WorldMember;
import tools.jackson.databind.ObjectMapper;

@Repository
public class RedisWorldEnterTokenCacheRepository
    extends AbstractRedisRepository<EnterToken, RedisWorldEnterTokenCacheRepository.EnterTokenCacheDto>
    implements WorldEnterTokenCacheRepository {

    @Value("${app.world.cache.enter-token}")
    private Duration enterTokenCacheTtl;

    protected RedisWorldEnterTokenCacheRepository(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        super(redisTemplate, objectMapper, EnterTokenCacheDto.class);
    }

    @Override
    protected Duration getTtl() {
        return enterTokenCacheTtl;
    }

    @Override
    public void save(EnterToken enterToken) {
        String key = "world:enter:token:" + enterToken.rawToken();
        set(key, enterToken);
    }

    @Override
    protected EnterTokenCacheDto toCacheModel(EnterToken domain) {
        return EnterTokenCacheDto.from(domain);
    }

    @Override
    protected EnterToken toDomainModel(EnterTokenCacheDto dto) {
        return null;
    }

    protected record EnterTokenCacheDto(
        long worldId,
        String playerId
    ) {
        public static EnterTokenCacheDto from(EnterToken domain) {
            return new EnterTokenCacheDto(
                domain.worldId(),
                domain.playerId().value()
            );
        }
    }
}