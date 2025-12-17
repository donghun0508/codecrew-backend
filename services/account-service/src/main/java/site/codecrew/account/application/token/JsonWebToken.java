package site.codecrew.account.application.token;

public record JsonWebToken(JsonWebTokenType type, String value) {

    public static JsonWebToken temporary(String value) {
        return new JsonWebToken(JsonWebTokenType.TEMPORARY, value);
    }

    public static JsonWebToken access(String value) {
        return new JsonWebToken(JsonWebTokenType.ACCESS, value);
    }

    public String withoutBearer() {
        return value.startsWith("Bearer ") ? value.substring(7) : value;
    }
}
