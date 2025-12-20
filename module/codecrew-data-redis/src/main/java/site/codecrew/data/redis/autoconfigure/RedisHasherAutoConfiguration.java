package site.codecrew.data.redis.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import site.codecrew.data.redis.hash.RedisValueHasher;
import site.codecrew.data.redis.hash.Sha256ValueHasher;

@AutoConfiguration
@ConditionalOnProperty(name = "redis.hasher.type", havingValue = "sha256", matchIfMissing = true)
public class RedisHasherAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean(RedisValueHasher.class)
    public RedisValueHasher sha256Hasher() {
        return new Sha256ValueHasher();
    }
}