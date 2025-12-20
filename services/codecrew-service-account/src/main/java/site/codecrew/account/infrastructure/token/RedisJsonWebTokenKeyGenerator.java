package site.codecrew.account.infrastructure.token;

import static site.codecrew.data.redis.RedisKeyConstants.CODECREW_APP_NAME;

import org.springframework.stereotype.Component;
import site.codecrew.account.application.token.JsonWebTokenType;

@Component
class RedisJsonWebTokenKeyGenerator {

    public String generate(JsonWebTokenType type, String subject) {
        return switch (type) {
            case TEMPORARY -> generateTemporaryKey(subject);
            case REFRESH -> generateRefreshKey(subject);
            case ACCESS -> throw new UnsupportedOperationException(
                "Access token은 Redis에 저장하지 않습니다"
            );
        };
    }

    private String generateTemporaryKey(String subject) {
        return String.join(":", CODECREW_APP_NAME, "signup", "pending", subject);
    }

    private String generateRefreshKey(String subject) {
        return String.join(":", CODECREW_APP_NAME, "auth", "session", "user", subject);
    }
}
