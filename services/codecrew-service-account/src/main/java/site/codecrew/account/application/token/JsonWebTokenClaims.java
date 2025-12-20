package site.codecrew.account.application.token;

import java.util.Map;
import java.util.stream.Collectors;

public record JsonWebTokenClaims(
    String subject,
    Map<ClaimKey, Object> claims
) {

    public Long getClaimAsLong() {
        return subject != null ? Long.valueOf(subject) : null;
    }

    public String getClaimAsString(ClaimKey key) {
        Object value = claims.get(key);
        return value != null ? value.toString() : null;
    }

    public static JsonWebTokenClaims of(Object subject) {
        return new JsonWebTokenClaims(subjectToString(subject), Map.of());
    }

    public static JsonWebTokenClaims of(Object subject, Map<ClaimKey, Object> claims) {
        return new JsonWebTokenClaims(subjectToString(subject), claims);
    }

    public static JsonWebTokenClaims fromMap(Map<ClaimKey, Object> claims) {
        Object subject = claims.get(ClaimKey.SUB);
        Map<ClaimKey, Object> others = claims.entrySet().stream()
            .filter(e -> e.getKey() != ClaimKey.SUB)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return new JsonWebTokenClaims(subjectToString(subject), others);
    }

    private static String subjectToString(Object subject) {
        return subject == null ? null : subject.toString();
    }
}
