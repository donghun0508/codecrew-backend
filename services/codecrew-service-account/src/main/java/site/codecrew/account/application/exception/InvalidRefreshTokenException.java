package site.codecrew.account.application.exception;

import site.codecrew.account.application.token.PlainToken;
import site.codecrew.core.exception.CoreException;

public class InvalidRefreshTokenException extends CoreException {

    public InvalidRefreshTokenException(PlainToken refreshToken) {
        super(AccountErrorCode.INVALID_REFRESH_TOKEN, "Invalid refresh token: " + refreshToken);
    }
}
