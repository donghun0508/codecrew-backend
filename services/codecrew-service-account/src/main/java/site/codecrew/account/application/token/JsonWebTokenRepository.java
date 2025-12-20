package site.codecrew.account.application.token;

import java.util.Optional;

public interface JsonWebTokenRepository {
    void save(JsonWebToken jsonWebToken);
    Optional<String> findByClaims(JsonWebTokenClaims claims);
}
