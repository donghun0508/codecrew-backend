package site.codecrew.account.application.token;

import static site.codecrew.account.application.token.JsonWebTokenType.REFRESH;
import static site.codecrew.account.application.token.JsonWebTokenType.TEMPORARY;

import java.time.Duration;
import java.time.Instant;

public record JsonWebToken(
    String subject,
    String rawToken,
    Duration ttl,
    Instant expiration,
    JsonWebTokenType type
) {

    public boolean isStorable() {
        return type == REFRESH || type == TEMPORARY;
    }
}