package site.codecrew.account.application.exception;

import static site.codecrew.core.exception.LogLevel.INFO;
import static site.codecrew.core.exception.LogLevel.WARN;

import site.codecrew.core.exception.ErrorCode;
import site.codecrew.core.exception.LogLevel;

public enum AccountErrorCode implements ErrorCode {

    TOKEN_EXPIRED(401, "T001", "토큰이 만료되었습니다", WARN),
    ;


    private final int httpStatus;
    private final String code;
    private final String message;
    private final LogLevel logLevel;

    AccountErrorCode(int httpStatus, String code, String message, LogLevel logLevel) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.logLevel = logLevel;
    }

    @Override public int httpStatus() { return httpStatus; }
    @Override public String code() { return code; }
    @Override public String message() { return message; }
    @Override public LogLevel logLevel() { return logLevel; }
}
