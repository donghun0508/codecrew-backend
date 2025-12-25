package site.codecrew.account.infrastructure.token;

import java.util.Optional;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import site.codecrew.account.application.token.ClaimKey;
import site.codecrew.account.application.token.JsonWebToken;
import site.codecrew.account.application.token.JsonWebTokenClaims;
import site.codecrew.account.application.token.JsonWebTokenRepository;
import site.codecrew.account.application.token.JsonWebTokenType;
import site.codecrew.data.redis.annotation.RedisReader;
import site.codecrew.data.redis.annotation.RedisWriter;

@Component
class RedisJsonWebTokenRepository implements JsonWebTokenRepository {

    private final StringRedisTemplate writeRedis;
    private final StringRedisTemplate readRedis;
    private final RedisJsonWebTokenKeyGenerator keyGenerator;
    private final RedisJsonWebTokenValuePreparer valueHasher;

    public RedisJsonWebTokenRepository(
        @RedisWriter StringRedisTemplate writeRedis,
        @RedisReader StringRedisTemplate readRedis,
        RedisJsonWebTokenKeyGenerator keyGenerator, RedisJsonWebTokenValuePreparer valueHasher
    ) {
        this.writeRedis = writeRedis;
        this.readRedis = readRedis;
        this.keyGenerator = keyGenerator;
        this.valueHasher = valueHasher;
    }

    @Override
    public void save(JsonWebToken jsonWebToken) {
        if (jsonWebToken.isStorable()) {
            String key = keyGenerator.generate(jsonWebToken.type(), jsonWebToken.subject());
            String preparedValue = valueHasher.prepare(jsonWebToken);

            writeRedis.opsForValue().set(key, preparedValue, jsonWebToken.ttl());
        }
    }

    @Override
    public void delete(JsonWebToken jsonWebToken) {
        String key = keyGenerator.generate(jsonWebToken.type(), jsonWebToken.subject());
        writeRedis.delete(key);
    }

    @Override
    public Optional<JsonWebToken> findByTypeAndSubject(JsonWebTokenType type, String subject) {
        String key = keyGenerator.generate(type, subject);

        String storedValue = readRedis.opsForValue().get(key);
        if (storedValue == null) {
            return Optional.empty();
        }
        JsonWebToken jsonWebToken = new JsonWebToken(subject, storedValue, null, null, type);
        return Optional.of(jsonWebToken);
    }
}
