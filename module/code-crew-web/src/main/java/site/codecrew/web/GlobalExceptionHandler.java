package site.codecrew.web;

import jakarta.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;
import site.codecrew.core.exception.ErrorCode;
import site.codecrew.core.http.ApiResponse;
import site.codecrew.core.http.ErrorResponse;
import site.codecrew.core.http.ExceptionResolver;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public final class GlobalExceptionHandler {

    private final ExceptionResolver exceptionResolver;

    @ExceptionHandler(CoreException.class)
    public ResponseEntity<ApiResponse<Void>> handleCoreException(CoreException ex) {
        logByPolicy(ex.getCode(), ex);

        ErrorResponse error = exceptionResolver.resolve(ex);
        return response(error);
    }

    @ExceptionHandler({
        MethodArgumentNotValidException.class,
        ConstraintViolationException.class,
        MethodArgumentTypeMismatchException.class,
        IllegalArgumentException.class
    })
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(Exception ex) {
        return handle(CoreErrorCode.VALIDATION_ERROR, ex);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDenied(AccessDeniedException ex) {
        return handle(CoreErrorCode.FORBIDDEN, ex);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(NoSuchElementException ex) {
        return handle(CoreErrorCode.NOT_FOUND, ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        return handle(CoreErrorCode.INTERNAL_ERROR, ex);
    }

    private ResponseEntity<ApiResponse<Void>> handle(CoreErrorCode code, Exception ex) {
        logByPolicy(code, ex);
        return response(ErrorResponse.from(code));
    }

    private void logByPolicy(ErrorCode code, Exception ex) {
        switch (code.logLevel()) {
            case ERROR -> log.error(
                "errorCode={}, message={}",
                code.code(),
                code.message(),
                ex
            );
            case WARN -> log.warn(
                "errorCode={}, message={}",
                code.code(),
                code.message(),
                ex
            );
            default -> log.info(
                "errorCode={}, message={}",
                code.code(),
                code.message(),
                ex
            );
        }
    }

    private ResponseEntity<ApiResponse<Void>> response(ErrorResponse error) {
        return ResponseEntity
            .status(error.status())
            .body(ApiResponse.error(error));
    }
}
