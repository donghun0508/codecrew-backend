package site.codecrew.data.redis.config;

import io.lettuce.core.ReadFrom;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.MasterSlaveServersConfig;
import org.redisson.config.ReadMode;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import site.codecrew.data.redis.annotation.RedisReader;
import site.codecrew.data.redis.annotation.RedisWriter;
import site.codecrew.data.redis.config.RedisProperties.RedisNodeInfo;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfiguration {

    private final RedisProperties redisProperties;

    public RedisConfiguration(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    @Primary
    @Bean
    public LettuceConnectionFactory defaultRedisConnectionFactory() {
        int database = redisProperties.database();
        RedisNodeInfo master = redisProperties.master();
        List<RedisNodeInfo> replicas = redisProperties.replicas();
        return lettuceConnectionFactory(
            database, master, replicas,
            builder -> builder.readFrom(ReadFrom.REPLICA_PREFERRED)
        );
    }

    @RedisWriter
    @Bean
    public LettuceConnectionFactory masterRedisConnectionFactory() {
        int database = redisProperties.database();
        RedisNodeInfo master = redisProperties.master();
        List<RedisNodeInfo> replicas = redisProperties.replicas();
        return lettuceConnectionFactory(
            database, master, replicas,
            builder -> builder.readFrom(ReadFrom.MASTER)
        );
    }

    @Primary
    @Bean
    public RedisTemplate<String, Object> defaultRedisTemplate(
        LettuceConnectionFactory lettuceConnectionFactory
    ) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        return configureRedisTemplate(redisTemplate, lettuceConnectionFactory);
    }

    @RedisWriter
    @Bean
    public RedisTemplate<String, Object> masterRedisTemplate(
        @RedisWriter LettuceConnectionFactory lettuceConnectionFactory
    ) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        return configureRedisTemplate(redisTemplate, lettuceConnectionFactory);
    }

    @Primary
    @RedisReader
    @Bean
    public StringRedisTemplate defaultStringRedisTemplate(
        LettuceConnectionFactory lettuceConnectionFactory
    ) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(lettuceConnectionFactory);
        template.afterPropertiesSet();
        return template;
    }

    @RedisWriter
    @Bean
    public StringRedisTemplate masterStringRedisTemplate(
        @RedisWriter LettuceConnectionFactory lettuceConnectionFactory
    ) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(lettuceConnectionFactory);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();

        String password = redisProperties.password();
        if (password != null && !password.isBlank() && !"null".equalsIgnoreCase(password)) {
            config.setPassword(password);
        }

        MasterSlaveServersConfig serverConfig = config.useMasterSlaveServers()
            .setMasterAddress("redis://" + redisProperties.master().host() + ":" + redisProperties.master().port())
            .setDatabase(redisProperties.database());

        serverConfig.setReadMode(ReadMode.MASTER);

        List<RedisNodeInfo> replicas = redisProperties.replicas();
        if (replicas != null && !replicas.isEmpty()) {
            replicas.forEach(replica ->
                serverConfig.addSlaveAddress("redis://" + replica.host() + ":" + replica.port())
            );
            serverConfig.setReadMode(ReadMode.SLAVE);
        }

        return Redisson.create(config);
    }

    private LettuceConnectionFactory lettuceConnectionFactory(
        int database,
        RedisNodeInfo master,
        List<RedisNodeInfo> replicas,
        Consumer<LettuceClientConfiguration.LettuceClientConfigurationBuilder> customizer
    ) {
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder =
            LettuceClientConfiguration.builder();

        if (redisProperties.timeout() != null) {
            builder.commandTimeout(redisProperties.timeout());
        }

        if (customizer != null) {
            customizer.accept(builder);
        }

        LettuceClientConfiguration clientConfig = builder.build();

        RedisStaticMasterReplicaConfiguration masterReplicaConfig =
            new RedisStaticMasterReplicaConfiguration(master.host(), master.port());
        masterReplicaConfig.setDatabase(database);

        if (Objects.nonNull(replicas)) {
            for (RedisNodeInfo replica : replicas) {
                masterReplicaConfig.addNode(replica.host(), replica.port());
            }
        }

        String password = redisProperties.password();
        if (password != null && !password.isBlank()) {
            masterReplicaConfig.setPassword(RedisPassword.of(password));
        }

        return new LettuceConnectionFactory(masterReplicaConfig, clientConfig);
    }

    private <K, V> RedisTemplate<K, V> configureRedisTemplate(
        RedisTemplate<K, V> template,
        LettuceConnectionFactory connectionFactory
    ) {
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        JsonMapper mapper = JsonMapper.builder()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();

        GenericJacksonJsonRedisSerializer jsonSerializer =
            new GenericJacksonJsonRedisSerializer(mapper);

        template.setValueSerializer(jsonSerializer);
        template.setHashValueSerializer(jsonSerializer);
        template.setConnectionFactory(connectionFactory);
        template.afterPropertiesSet();

        return template;
    }
}
