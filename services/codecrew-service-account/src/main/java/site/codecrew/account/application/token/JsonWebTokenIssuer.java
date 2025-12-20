package site.codecrew.account.application.token;

public interface JsonWebTokenIssuer {

    JsonWebToken issue(JsonWebTokenType type, JsonWebTokenClaims claims);
}
