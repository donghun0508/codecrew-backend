package site.codecrew.account.adapter.api.v1.response;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import site.codecrew.account.adapter.api.v1.response.JsonWebTokenResponse.AuthenticatedJsonWebTokenResponse;
import site.codecrew.account.adapter.api.v1.response.JsonWebTokenResponse.PendingJsonWebTokenResponse;
import site.codecrew.account.application.auth.AuthenticatedTokenResult;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = AuthenticatedJsonWebTokenResponse.class, name = "authenticated"),
    @JsonSubTypes.Type(value = PendingJsonWebTokenResponse.class, name = "pending")
})
public sealed interface JsonWebTokenResponse
    permits AuthenticatedJsonWebTokenResponse, PendingJsonWebTokenResponse {

    static JsonWebTokenResponse from(AuthenticatedTokenResult result) {
        if (result.accessToken() != null) {
            return AuthenticatedJsonWebTokenResponse.from(result);
        } else {
            return PendingJsonWebTokenResponse.from(result);
        }
    }

    record AuthenticatedJsonWebTokenResponse(
        String accessToken,
        String refreshToken
    ) implements JsonWebTokenResponse {
        public static AuthenticatedJsonWebTokenResponse from(AuthenticatedTokenResult result) {
            return new AuthenticatedJsonWebTokenResponse(result.accessToken().rawToken(), result.refreshToken().rawToken());
        }
    }

    record PendingJsonWebTokenResponse(
        String temporaryToken
    ) implements JsonWebTokenResponse {
        public static PendingJsonWebTokenResponse from(AuthenticatedTokenResult result) {
            return new PendingJsonWebTokenResponse(result.temporaryToken().rawToken());
        }
    }
}
