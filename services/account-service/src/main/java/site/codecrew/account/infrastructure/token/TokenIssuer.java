package site.codecrew.account.infrastructure.token;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import site.codecrew.account.application.token.JsonWebTokenType;
import site.codecrew.account.config.JsonWebTokenProperties;
import site.codecrew.account.config.JsonWebTokenProperties.TokenClaims;
import site.codecrew.account.config.JsonWebTokenProperties.TokenExpSeconds;
import site.codecrew.account.domain.Member;
import site.codecrew.account.domain.SocialProfile;

@Component
public class TokenIssuer {

    private final TokenExpSeconds tokenExpSeconds;
    private final TokenClaims tokenClaims;
    private final SecretKey key;
    private static final long EXPIRATION_MULTIPLIER = 1000L;

    public TokenIssuer(JsonWebTokenProperties jsonWebTokenProperties) {
        this.tokenExpSeconds = jsonWebTokenProperties.getTokenExpSeconds();
        this.tokenClaims = jsonWebTokenProperties.getTokenClaims();
        this.key = Keys.hmacShaKeyFor(
            io.jsonwebtoken.io.Decoders.BASE64.decode(jsonWebTokenProperties.getSecret())
        );
    }

    public String issue(Member member) {
        return newTokenBuilder(JsonWebTokenType.ACCESS)
            .subject(member.id().toString())
            .claim(ClaimsConst.EMAIL, member.email())
            .compact();
    }

    public String issue(SocialProfile profile) {
        return newTokenBuilder(JsonWebTokenType.TEMPORARY)
            .subject(profile.sub())
            .claim(ClaimsConst.EMAIL, profile.email())
            .claim(ClaimsConst.NAME, profile.name())
            .claim(ClaimsConst.PICTURE, profile.picture())
            .claim(ClaimsConst.SOCIAL_TYPE, profile.socialType().name())
            .compact();
    }

    private JwtBuilder newTokenBuilder(JsonWebTokenType type) {
        long now = System.currentTimeMillis();
        long expMs = getExpirationMillis(type);
        return Jwts.builder()
            .header().type(ClaimsConst.HEADER_TYPE).and()
            .issuer(tokenClaims.getIssuer())
            .audience().add(tokenClaims.getAudience()).and()
            .id(UUID.randomUUID().toString())
            .issuedAt(new Date(now))
            .expiration(new Date(System.currentTimeMillis() + expMs))
            .claim(ClaimsConst.TYPE, type.value())
            .signWith(key, Jwts.SIG.HS512);
    }

    private long getExpirationMillis(JsonWebTokenType tokenType) {
        return switch (tokenType) {
            case ACCESS -> tokenExpSeconds.getAccess() * EXPIRATION_MULTIPLIER;
            case TEMPORARY -> tokenExpSeconds.getTemporary() * EXPIRATION_MULTIPLIER;
        };
    }

    private static final class ClaimsConst {

        private static final String HEADER_TYPE = "JWT";
        private static final String EMAIL = "email";
        private static final String NAME = "name";
        private static final String PICTURE = "picture";
        private static final String SOCIAL_TYPE = "socialType";
        private static final String TYPE = "type";
    }

}
