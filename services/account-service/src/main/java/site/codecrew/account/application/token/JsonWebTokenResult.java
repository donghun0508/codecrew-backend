package site.codecrew.account.application.token;

public record JsonWebTokenResult(JsonWebTokenType jsonWebTokenType, String token) {

    public static JsonWebTokenResult from(JsonWebToken token) {
        return new JsonWebTokenResult(JsonWebTokenType.ACCESS, token.value());
    }
}
