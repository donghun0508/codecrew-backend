package site.codecrew.account.infrastructure.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.EnumMap;
import java.util.Objects;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import site.codecrew.account.application.exception.AccountErrorCode;
import site.codecrew.account.application.exception.TokenException;
import site.codecrew.account.application.token.ClaimKey;
import site.codecrew.account.application.token.JsonWebTokenClaims;
import site.codecrew.account.application.token.JsonWebTokenParser;
import site.codecrew.account.application.token.PlainToken;
import site.codecrew.account.application.exception.TokenExpiredException;
import site.codecrew.account.config.JsonWebTokenProperties;

@Component
class JsonWebTokenParserImpl implements JsonWebTokenParser {

    private final JwtParser jwtParser;

    public JsonWebTokenParserImpl(JsonWebTokenProperties properties) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(properties.getSecret()));
        this.jwtParser = Jwts.parser().verifyWith(key).build();
    }

    @Override
    public JsonWebTokenClaims parse(PlainToken plainToken) {
        try {
            Claims claims = jwtParser.parseSignedClaims(plainToken.rawToken())
                .getPayload();

            String subject = claims.getSubject();

            EnumMap<ClaimKey, Object> customClaims = new EnumMap<>(ClaimKey.class);
            for (ClaimKey key : ClaimKey.values()) {
                if (key == ClaimKey.SUB) {
                    continue;
                }

                Object value = claims.get(key.getKey());
                if (value != null) {
                    customClaims.put(key, value);
                }
            }

            return new JsonWebTokenClaims(subject, customClaims);
        } catch (ExpiredJwtException e) {
            String subject = plainToken.rawToken();
            if (Objects.nonNull(e.getClaims())) {
                Claims expiredClaims = e.getClaims();
                subject = expiredClaims.getSubject();
            }
            throw new TokenExpiredException(subject);
        } catch (JwtException e) {
            throw new TokenException(AccountErrorCode.TOKEN_INVALID, e);
        }
    }
}