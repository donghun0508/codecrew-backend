package site.codecrew.core.http;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;

@RequiredArgsConstructor
@RestControllerAdvice
public final class GlobalExceptionHandler {

    private final ExceptionResolver exceptionResolver;
    private final ErrorReporter errorReporter;

    @ExceptionHandler(CoreException.class)
    public ResponseEntity<ApiResponse<Void>> handleCoreException(CoreException ex) {
        errorReporter.send(ex.getCode(), ex);

        ErrorResponse error = exceptionResolver.resolve(ex);
        return response(error);
    }

    @ExceptionHandler({
        MethodArgumentNotValidException.class,
        ConstraintViolationException.class,
        MethodArgumentTypeMismatchException.class,
        IllegalArgumentException.class,
        UnexpectedTypeException.class,
        HttpMessageNotReadableException.class
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
        errorReporter.send(code, ex);
        return response(ErrorResponse.from(code));
    }

    public static ResponseEntity<ApiResponse<Void>> response(ErrorResponse error) {
        return ResponseEntity.status(error.status()).body(ApiResponse.error(error));
    }
}