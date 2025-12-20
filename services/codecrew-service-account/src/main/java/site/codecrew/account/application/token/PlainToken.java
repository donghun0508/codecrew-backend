package site.codecrew.account.application.token;

public record PlainToken(JsonWebTokenType type, String rawToken) {

    public static PlainToken access(String value) {
        return new PlainToken(JsonWebTokenType.ACCESS, extractRawToken(value));
    }

    public static PlainToken temporary(String value) {
        return new PlainToken(JsonWebTokenType.TEMPORARY, extractRawToken(value));
    }

    private static String extractRawToken(String bearerToken) {
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid bearer token");
        }
        return bearerToken.substring(7);
    }

    public static PlainToken from(JsonWebToken token) {
        return new PlainToken(token.type(), token.rawToken());
    }
}
