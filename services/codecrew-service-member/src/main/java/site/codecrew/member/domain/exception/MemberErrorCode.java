package site.codecrew.member.domain.exception;

import site.codecrew.core.exception.ErrorCode;
import site.codecrew.core.exception.LogLevel;

public enum MemberErrorCode implements ErrorCode {

    NICKNAME_DUPLICATED(409, "M001", "이미 등록된 닉네임입니다.", LogLevel.DEBUG);
    ;

    private final int httpStatus;
    private final String code;
    private final String message;
    private final LogLevel logLevel;

    MemberErrorCode(int httpStatus, String code, String message, LogLevel logLevel) {
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