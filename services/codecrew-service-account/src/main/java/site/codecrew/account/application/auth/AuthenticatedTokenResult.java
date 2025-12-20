package site.codecrew.account.application.auth;

import site.codecrew.account.application.token.AuthenticatedToken;
import site.codecrew.account.application.token.JsonWebToken;

public record AuthenticatedTokenResult(
    JsonWebToken accessToken,
    JsonWebToken refreshToken,
    JsonWebToken temporaryToken
) {
    public static AuthenticatedTokenResult from(AuthenticatedToken token) {
        return switch (token) {
            case AuthenticatedToken.Authenticated(var access, var refresh) ->
                new AuthenticatedTokenResult(access, refresh, null);
            case AuthenticatedToken.Pending(var temporary) ->
                new AuthenticatedTokenResult(null, null, temporary);
        };
    }
}
