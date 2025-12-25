package site.codecrew.account.application.exception;

import site.codecrew.core.exception.CoreException;

public class TokenException extends CoreException {

    public TokenException(AccountErrorCode accountErrorCode) {
        super(accountErrorCode);
    }

    public TokenException(AccountErrorCode accountErrorCode, Throwable cause) {
        super(accountErrorCode, cause);
    }
}
