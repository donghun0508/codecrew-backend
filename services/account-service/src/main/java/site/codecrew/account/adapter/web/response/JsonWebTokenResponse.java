package site.codecrew.account.adapter.web.response;


import site.codecrew.account.application.token.JsonWebTokenResult;
import site.codecrew.account.application.token.JsonWebTokenType;

public record JsonWebTokenResponse(JsonWebTokenType tokenType, String token) {

    public static JsonWebTokenResponse from(JsonWebTokenResult result) {
        return new JsonWebTokenResponse(result.jsonWebTokenType(), result.token());
    }
}
