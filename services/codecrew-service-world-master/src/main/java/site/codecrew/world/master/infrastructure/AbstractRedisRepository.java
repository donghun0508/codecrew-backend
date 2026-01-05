package site.codecrew.world.master.infrastructure;

import java.time.Duration;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import tools.jackson.databind.ObjectMapper;

@Slf4j
public abstract class AbstractRedisRepository<D, C> implements WorldRedisRepository<D> {

    protected final StringRedisTemplate redisTemplate;
    protected final ObjectMapper objectMapper;
    private final Class<C> cacheType;

    protected AbstractRedisRepository(StringRedisTemplate redisTemplate, ObjectMapper objectMapper, Class<C> cacheType) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.cacheType = cacheType;
    }

    protected abstract Duration getTtl();

    protected abstract C toCacheModel(D domain);
    protected abstract D toDomainModel(C cacheModel);

    @Override
    public void set(String key, D domain) {
        C cacheModel = toCacheModel(domain);
        String value = objectMapper.writeValueAsString(cacheModel);

        Duration ttl = getTtl();
        if (ttl == null || ttl.isZero()) {
            redisTemplate.opsForValue().set(key, value);
        } else {
            redisTemplate.opsForValue().set(key, value, ttl);
        }
    }

    @Override
    public Optional<D> find(String key) {
        String value = redisTemplate.opsForValue().get(key);

        if (value == null || value.isBlank()) {
            return Optional.empty();
        }
        C cacheModel = objectMapper.readValue(value, cacheType);
        return Optional.ofNullable(toDomainModel(cacheModel));
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}