package site.codecrew.account.infrastructure.token;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import site.codecrew.account.application.token.ClaimKey;
import site.codecrew.account.application.token.JsonWebToken;
import site.codecrew.account.application.token.JsonWebTokenClaims;
import site.codecrew.account.application.token.JsonWebTokenIssuer;
import site.codecrew.account.application.token.JsonWebTokenType;
import site.codecrew.account.config.JsonWebTokenProperties;
import site.codecrew.account.config.JsonWebTokenProperties.TokenClaims;
import site.codecrew.account.config.JsonWebTokenProperties.TokenExpSeconds;

@Component
class JsonWebTokenIssuerImpl implements JsonWebTokenIssuer {

    private final TokenExpSeconds tokenExpSeconds;
    private final TokenClaims tokenClaims;
    private final SecretKey key;

    private static final long EXPIRATION_MULTIPLIER = 1000L;

    public JsonWebTokenIssuerImpl(JsonWebTokenProperties properties) {
        this.tokenExpSeconds = properties.getTokenExpSeconds();
        this.tokenClaims = properties.getTokenClaims();
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(properties.getSecret()));
    }

    @Override
    public JsonWebToken issue(JsonWebTokenType type, JsonWebTokenClaims claims) {
        UnsignedTokenContext context = buildUnsignedToken(type, claims);
        String signedToken = signToken(context.builder());

        return new JsonWebToken(
            claims.subject(),
            signedToken,
            Duration.ofMillis(context.expirationMillis()),
            context.expirationDate().toInstant(),
            type
        );
    }

    private UnsignedTokenContext buildUnsignedToken(JsonWebTokenType type, JsonWebTokenClaims claims) {
        long currentTimeMillis = System.currentTimeMillis();
        long expirationMillis = calculateExpirationMillis(type);
        Date issuedAt = new Date(currentTimeMillis);
        Date expirationDate = new Date(currentTimeMillis + expirationMillis);

        JwtBuilder builder = Jwts.builder()
            .header().type("JWT").and()
            .issuer(tokenClaims.getIssuer())
            .audience().add(tokenClaims.getAudience()).and()
            .id(UUID.randomUUID().toString())
            .issuedAt(issuedAt)
            .expiration(expirationDate)
            .claim(ClaimKey.TYPE.getKey(), type.value())
            .subject(claims.subject());

        addCustomClaims(builder, claims.claims());

        return new UnsignedTokenContext(builder, expirationMillis, expirationDate);
    }

    private void addCustomClaims(JwtBuilder builder, Map<ClaimKey, Object> claims) {
        if (claims != null && !claims.isEmpty()) {
            claims.forEach((claimKey, value) -> builder.claim(claimKey.getKey(), value));
        }
    }

    private String signToken(JwtBuilder builder) {
        return builder.signWith(key, Jwts.SIG.HS512).compact();
    }

    private long calculateExpirationMillis(JsonWebTokenType type) {
        return switch (type) {
            case ACCESS -> tokenExpSeconds.getAccess() * EXPIRATION_MULTIPLIER;
            case REFRESH -> tokenExpSeconds.getRefresh() * EXPIRATION_MULTIPLIER;
            case TEMPORARY -> tokenExpSeconds.getTemporary() * EXPIRATION_MULTIPLIER;
        };
    }

    private record UnsignedTokenContext(
        JwtBuilder builder,
        long expirationMillis,
        Date expirationDate
    ) {}
}