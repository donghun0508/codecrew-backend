package site.codecrew.account.application.token;

public record PlainToken(JsonWebTokenType type, String rawToken) {

    public static PlainToken access(String value) {
        return new PlainToken(JsonWebTokenType.ACCESS, extractRawToken(value));
    }

    public static PlainToken temporary(String value) {
        return new PlainToken(JsonWebTokenType.TEMPORARY, extractRawToken(value));
    }

    public static PlainToken refresh(String value) {
        return new PlainToken(JsonWebTokenType.REFRESH, extractRawToken(value));
    }

    private static String extractRawToken(String token) {
        if (token == null) {
            throw new IllegalArgumentException("Token is null");
        }
        String prefix = "Bearer ";
        return token.startsWith(prefix) ? token.substring(prefix.length()) : token;
    }

    public static PlainToken from(JsonWebToken token) {
        return new PlainToken(token.type(), token.rawToken());
    }
}
