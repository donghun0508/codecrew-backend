package site.codecrew.core.http;

import static site.codecrew.core.exception.CoreErrorCode.INTERNAL_ERROR;

import site.codecrew.core.exception.ErrorCode;

public record ErrorResponse(
    int status,
    String code,
    String message
) {
    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(
            errorCode.httpStatus(),
            errorCode.code(),
            errorCode.message()
        );
    }

    public static ErrorResponse internalError() {
        return ErrorResponse.from(INTERNAL_ERROR);
    }
}