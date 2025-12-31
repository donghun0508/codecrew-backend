package site.codecrew.apigateway.util;

import java.net.URL;
import java.util.Objects;
import org.springframework.security.oauth2.jwt.Jwt;

public final class JwtClaims {

    private JwtClaims() {}

    public static String issuer(Jwt jwt) {
        Objects.requireNonNull(jwt, "jwt");
        URL issuer = jwt.getIssuer();
        return issuer == null ? null : issuer.toString();
    }

    public static String subject(Jwt jwt) {
        Objects.requireNonNull(jwt, "jwt");
        return jwt.getSubject();
    }

    public static String preferredUsername(Jwt jwt) {
        Objects.requireNonNull(jwt, "jwt");
        return jwt.getClaimAsString("preferred_username");
    }

    public static String email(Jwt jwt) {
        Objects.requireNonNull(jwt, "jwt");
        return jwt.getClaimAsString("email");
    }

    public static String username(Jwt jwt) {
        Objects.requireNonNull(jwt, "jwt");

        String family = jwt.getClaimAsString("family_name");
        String given = jwt.getClaimAsString("given_name");
        String full = joinWithSpaceIfPresent(family, given);
        if (isNotBlank(full)) {
            return full;
        }

        return null;
    }

    public static String realm(Jwt jwt) {
        Objects.requireNonNull(jwt, "jwt");
        return extractLastPathSegment(jwt.getIssuer());
    }

    private static String joinWithSpaceIfPresent(String a, String b) {
        if (!isNotBlank(a) && !isNotBlank(b)) {
            return null;
        }
        if (!isNotBlank(a)) {
            return b;
        }
        if (!isNotBlank(b)) {
            return a;
        }
        return a + b;
    }

    private static boolean isNotBlank(String s) {
        return s != null && !s.isBlank();
    }

    private static String extractLastPathSegment(URL url) {
        if (url == null) {
            return null;
        }
        String path = url.getPath();
        if (!isNotBlank(path)) {
            return null;
        }
        int idx = path.lastIndexOf('/');
        return idx >= 0 ? path.substring(idx + 1) : path;
    }
}