package site.codecrew.core.http;

import site.codecrew.core.exception.ErrorCode;

public final class ExceptionMessage {

    private final String code;
    private final String message;

    private ExceptionMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ExceptionMessage from(ErrorCode errorCode) {
        return new ExceptionMessage(
            errorCode.code(),
            errorCode.message()
        );
    }

    public static ExceptionMessage from(ErrorCode errorCode, Object detail) {
        return new ExceptionMessage(
            errorCode.code(),
            errorCode.message() + " (" + detail + ")"
        );
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}
