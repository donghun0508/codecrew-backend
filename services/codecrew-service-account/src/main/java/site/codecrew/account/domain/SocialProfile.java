package site.codecrew.account.domain;

import java.util.Map;
import site.codecrew.account.application.token.ClaimKey;
import site.codecrew.account.application.token.JsonWebTokenClaims;

public record SocialProfile(SocialType socialType, String sub, String email, String name, String picture) {

    public JsonWebTokenClaims toClaims() {
        return new JsonWebTokenClaims(sub,
            Map.of(
                ClaimKey.SOCIAL_TYPE, socialType().name(),
                ClaimKey.EMAIL, email(),
                ClaimKey.NAME, name(),
                ClaimKey.PICTURE, picture())
        );
    }

    public static SocialProfile fromClaims(JsonWebTokenClaims claims) {
        return new SocialProfile(
            SocialType.valueOf((String) claims.claims().get(ClaimKey.SOCIAL_TYPE)),
            claims.subject(),
            (String) claims.claims().get(ClaimKey.EMAIL),
            (String) claims.claims().get(ClaimKey.NAME),
            (String) claims.claims().get(ClaimKey.PICTURE)
        );
    }
}
