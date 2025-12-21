package site.codecrew.data.redis.config;

import java.time.Duration;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.data.redis")
public record RedisProperties(
    int database,
    Duration timeout,
    String password,
    RedisNodeInfo master,
    List<RedisNodeInfo> replicas
) {
    public record RedisNodeInfo(
        String host,
        int port
    ) {}
}
