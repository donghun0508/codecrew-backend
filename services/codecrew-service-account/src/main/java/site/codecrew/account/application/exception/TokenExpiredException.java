package site.codecrew.account.application.exception;

import site.codecrew.core.exception.CoreException;

public class TokenExpiredException extends CoreException {

    public TokenExpiredException(String subject) {
        super(AccountErrorCode.TOKEN_EXPIRED, "Token has expired for subject: " + subject);
    }
}
