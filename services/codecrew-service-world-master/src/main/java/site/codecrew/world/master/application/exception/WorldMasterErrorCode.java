package site.codecrew.world.master.application.exception;

import site.codecrew.core.exception.ErrorCode;
import site.codecrew.core.exception.LogLevel;

public enum WorldMasterErrorCode implements ErrorCode {
    WORLD_ENTRY_FORBIDDEN(403, "W001", "현재 월드에 입장할 수 있는 권한이 없습니다.", LogLevel.DEBUG),
    WORLD_NOT_FOUND(404, "W002", "월드를 찾을 수 없습니다.", LogLevel.WARN),
    AVATAR_NOT_FOUND(404, "W003", "아바타를 찾을 수 없습니다.", LogLevel.DEBUG),
    WORLD_NOT_AVAILABLE(503, "W004", "현재 월드가 이용 불가능한 상태입니다.", LogLevel.INFO),
    WORLD_UNDER_MAINTENANCE(503, "W005", "월드 점검 중입니다.", LogLevel.INFO),
    WORLD_FULL(409, "W006", "월드 인원이 가득 찼습니다.", LogLevel.INFO),
    SERVER_NODE_INVALID_FORMAT(500, "W007", "서버 노드의 형식이 올바르지 않습니다.", LogLevel.ERROR)
    ;

    private final int httpStatus;
    private final String code;
    private final String message;
    private final LogLevel logLevel;

    WorldMasterErrorCode(int httpStatus, String code, String message, LogLevel logLevel) {
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