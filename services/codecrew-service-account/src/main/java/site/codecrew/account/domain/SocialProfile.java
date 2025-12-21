package site.codecrew.account.domain;

import static java.util.Map.entry;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import site.codecrew.account.application.token.ClaimKey;
import site.codecrew.account.application.token.JsonWebTokenClaims;

public record SocialProfile(SocialType socialType, String sub, String email, String name, String picture) {

    public JsonWebTokenClaims toClaims() {
        Map<ClaimKey, Object> claims = new EnumMap<>(ClaimKey.class);
        claims.put(ClaimKey.SOCIAL_TYPE, socialType().name());

        Optional.ofNullable(email()).ifPresent(v -> claims.put(ClaimKey.EMAIL, v));
        Optional.ofNullable(name()).ifPresent(v -> claims.put(ClaimKey.NAME, v));
        Optional.ofNullable(picture()).ifPresent(v -> claims.put(ClaimKey.PICTURE, v));

        return new JsonWebTokenClaims(sub, claims);
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
