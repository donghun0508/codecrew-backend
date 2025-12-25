package site.codecrew.account.application.token;

import java.util.Optional;

public interface JsonWebTokenRepository {
    void save(JsonWebToken jsonWebToken);
    void delete(JsonWebToken jsonWebToken);
    Optional<JsonWebToken> findByTypeAndSubject(JsonWebTokenType type, String subject);
}
