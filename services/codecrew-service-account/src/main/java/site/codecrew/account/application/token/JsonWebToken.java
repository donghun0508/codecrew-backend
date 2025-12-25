package site.codecrew.account.application.token;

import static site.codecrew.account.application.token.JsonWebTokenType.REFRESH;
import static site.codecrew.account.application.token.JsonWebTokenType.TEMPORARY;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JsonWebToken that = (JsonWebToken) o;
        return Objects.equals(rawToken(), that.rawToken());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(rawToken());
    }
}