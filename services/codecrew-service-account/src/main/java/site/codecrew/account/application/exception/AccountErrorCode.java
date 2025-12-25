package site.codecrew.account.application.exception;

import static site.codecrew.core.exception.LogLevel.WARN;

import site.codecrew.core.exception.ErrorCode;
import site.codecrew.core.exception.LogLevel;

public enum AccountErrorCode implements ErrorCode {

    TOKEN_EXPIRED(401, "T001", "토큰이 만료되었습니다", null, WARN),
    INVALID_REFRESH_TOKEN(401, "T002", "인증 정보가 유효하지 않습니다.", "비정상적인 로그인 시도가 감지되었습니다. 보안을 위해 로그아웃되었습니다.",  WARN),
    TOKEN_INVALID(401, "T003", "토큰이 유효하지 않습니다.", null, WARN),
    ;

    private final int httpStatus;
    private final String code;
    private final String message;
    private final String detailMessage;
    private final LogLevel logLevel;

    AccountErrorCode(int httpStatus, String code, String message, String detailMessage, LogLevel logLevel) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.detailMessage = detailMessage;
        this.logLevel = logLevel;
    }

    @Override
    public int httpStatus() {
        return httpStatus;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public LogLevel logLevel() {
        return logLevel;
    }

    @Override
    public String detailMessage() {
        return detailMessage;
    }
}
