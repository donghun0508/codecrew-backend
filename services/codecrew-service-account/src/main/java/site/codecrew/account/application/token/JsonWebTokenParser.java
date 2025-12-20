package site.codecrew.account.application.token;

public interface JsonWebTokenParser {

    JsonWebTokenClaims parse(PlainToken plainToken);
}
