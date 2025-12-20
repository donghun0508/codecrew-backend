package site.codecrew.account.application.token;

public sealed interface AuthenticatedToken {

    record Authenticated(
        JsonWebToken accessToken,
        JsonWebToken refreshToken
    ) implements AuthenticatedToken {}

    record Pending(
        JsonWebToken temporaryToken
    ) implements AuthenticatedToken {}
}
