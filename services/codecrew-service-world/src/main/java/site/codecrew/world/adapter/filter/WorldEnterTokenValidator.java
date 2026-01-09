package site.codecrew.world.adapter.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class WorldEnterTokenValidator {

    private final ReactiveStringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public Mono<PlayerPrincipal> validate(String token) {
        log.info("Validating enter token: {}", token);
        String key = "world:enter:token:" + token;

        return redisTemplate.opsForValue().get(key)
            .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid enter token")))
            .flatMap(json -> redisTemplate.delete(key).then(deserialize(json)));
    }

    private Mono<PlayerPrincipal> deserialize(String json) {
        return Mono.just(objectMapper.readValue(json, PlayerPrincipal.class));
    }
}

//        return redisTemplate.opsForValue().get(key)
//            .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid enter token")))
//            .flatMap(this::deserialize);