package site.codecrew.core.util;

import java.util.Optional;

public final class BearerTokenUtils {

    private static final String PREFIX = "Bearer ";

    private BearerTokenUtils() {
    }

    public static String stripBearerPrefix(String value) {
        if (value == null) {
            return null;
        }
        return value.startsWith(PREFIX) ? value.substring(PREFIX.length()) : value;
    }

    public static Optional<String> extractBearerToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith(PREFIX)) {
            return Optional.empty();
        }
        String token = authorizationHeader.substring(PREFIX.length()).trim();
        return token.isEmpty() ? Optional.empty() : Optional.of(token);
    }
}