package site.codecrew.world.domain.exception;

import site.codecrew.core.exception.ErrorCode;
import site.codecrew.core.exception.LogLevel;

public enum WorldErrorCode implements ErrorCode {

    DUPLICATE_SESSION(409, "W001", "이미 접속 중입니다.", LogLevel.INFO),
    WORLD_FULL(503, "W002", "월드 정원이 초과되었습니다.", LogLevel.INFO),
    ;

    private final int httpStatus;
    private final String code;
    private final String message;
    private final LogLevel logLevel;

    WorldErrorCode(int httpStatus, String code, String message, LogLevel logLevel) {
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