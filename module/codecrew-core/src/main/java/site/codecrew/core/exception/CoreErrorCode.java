package site.codecrew.core.exception;

import static site.codecrew.core.exception.LogLevel.*;

public enum CoreErrorCode implements ErrorCode {

    INTERNAL_ERROR(500, "C001", "서버 내부 오류가 발생했습니다.", ERROR),

    INVALID_ARGUMENT(400, "C002", "잘못된 인자값입니다.", INFO),
    INVALID_REQUEST(400, "C003", "잘못된 요청입니다.", INFO),
    VALIDATION_ERROR(400, "C004", "유효하지 않은 요청입니다.", INFO),
    NOT_FOUND(404, "C005", "요청한 리소스를 찾을 수 없습니다.", INFO),

    UNAUTHORIZED(401, "C006", "인증이 필요합니다.", WARN),
    FORBIDDEN(403, "C007", "접근 권한이 없습니다.", WARN),

    CONFLICT(409, "C008", "이미 존재하거나 충돌되는 데이터가 있습니다.", INFO);

    private final int httpStatus;
    private final String code;
    private final String message;
    private final LogLevel logLevel;

    CoreErrorCode(int httpStatus, String code, String message, LogLevel logLevel) {
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
