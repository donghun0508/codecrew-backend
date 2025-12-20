package site.codecrew.account.infrastructure.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.codecrew.account.application.token.JsonWebToken;
import site.codecrew.data.redis.hash.RedisValueHasher;

@RequiredArgsConstructor
@Component
class RedisJsonWebTokenValuePreparer {

    private final RedisValueHasher valueHasher;

    public String prepare(JsonWebToken token) {
        return switch (token.type()) {
            case REFRESH -> token.rawToken();                      // 원본 저장 (재사용 필요)
            case TEMPORARY -> valueHasher.hash(token.rawToken());  // 해시 저장 (검증만)
            case ACCESS -> throw new UnsupportedOperationException(
                "Access token은 Redis에 저장하지 않습니다"
            );
        };
    }

    public boolean matches(JsonWebToken token, String storedValue) {
        return switch (token.type()) {
            case REFRESH -> token.rawToken().equals(storedValue);  // 원본 비교
            case TEMPORARY -> valueHasher.matches(token.rawToken(), storedValue);  // 해시 비교
            case ACCESS -> throw new UnsupportedOperationException(
                "Access token은 Redis에 저장하지 않습니다"
            );
        };
    }
}
