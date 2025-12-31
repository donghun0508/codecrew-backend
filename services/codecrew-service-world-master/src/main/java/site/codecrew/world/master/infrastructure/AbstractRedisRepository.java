package site.codecrew.world.master.infrastructure;

import java.time.Duration;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import tools.jackson.databind.ObjectMapper;

@Slf4j
public abstract class AbstractRedisRepository<T> implements WorldRedisRepository<T> {

    protected final StringRedisTemplate redisTemplate;
    protected final ObjectMapper objectMapper;
    protected final Class<T> classType;

    protected AbstractRedisRepository(StringRedisTemplate redisTemplate, ObjectMapper objectMapper, Class<T> classType) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.classType = classType;
    }

    protected abstract Duration getTtl();

    @Override
    public void set(String key, T data) {
        String value = objectMapper.writeValueAsString(data);
        Duration ttl = getTtl();

        if (ttl.isZero()) {
            redisTemplate.opsForValue().set(key, value);
        } else {
            redisTemplate.opsForValue().set(key, value, ttl);
        }
    }

    @Override
    public Optional<T> find(String key) {
        String value = redisTemplate.opsForValue().get(key);

        if (value == null || value.isBlank()) {
            return Optional.empty();
        }
        return Optional.of(objectMapper.readValue(value, classType));
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}