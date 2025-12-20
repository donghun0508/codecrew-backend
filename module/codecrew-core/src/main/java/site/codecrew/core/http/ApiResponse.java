package site.codecrew.core.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import site.codecrew.core.exception.ErrorCode;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiResponse<T> {

    private final Status status;
    private final T data;
    private final Error error;
    private final Map<String, Object> meta;
    private final Instant timestamp;

    private ApiResponse(
        @JsonProperty("status") Status status,
        @JsonProperty("data") T data,
        @JsonProperty("error") Error error,
        @JsonProperty("meta") Map<String, Object> meta
    ) {
        this.status = status;
        this.data = data;
        this.error = error;
        this.meta = meta == null ? Collections.emptyMap() : Map.copyOf(meta);
        this.timestamp = Instant.now();
    }

    /* ===================== success ===================== */

    public static ApiResponse<Void> success() {
        return new ApiResponse<>(Status.SUCCESS, null, null, null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(Status.SUCCESS, data, null, null);
    }

    public static <T> ApiResponse<T> success(T data, Map<String, Object> meta) {
        return new ApiResponse<>(Status.SUCCESS, data, null, meta);
    }

    /* ===================== error ===================== */

    /* ===== error (기본) ===== */

    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return new ApiResponse<>(
            Status.ERROR,
            null,
            Error.from(errorCode),
            null
        );
    }

    public static <T> ApiResponse<T> error(ExceptionMessage message) {
        return new ApiResponse<>(
            Status.ERROR,
            null,
            Error.from(message),
            null
        );
    }


    public static <T> ApiResponse<T> error(
        ErrorCode errorCode,
        Map<String, Object> meta
    ) {
        return new ApiResponse<>(
            Status.ERROR,
            null,
            Error.from(errorCode),
            meta
        );
    }

    public static <T> ApiResponse<T> error(
        ExceptionMessage message,
        Map<String, Object> meta
    ) {
        return new ApiResponse<>(
            Status.ERROR,
            null,
            Error.from(message),
            meta
        );
    }

    /* ===== error + data (드물지만 실제 있음) ===== */

    public static <T> ApiResponse<T> error(
        ErrorCode errorCode,
        T data
    ) {
        return new ApiResponse<>(
            Status.ERROR,
            data,
            Error.from(errorCode),
            null
        );
    }

    public static <T> ApiResponse<T> error(ErrorResponse error) {
        return new ApiResponse<>(
            Status.ERROR,
            null,
            new Error(error.code(), error.message()),
            null
        );
    }

    /* ===================== meta ===================== */

    public ApiResponse<T> withMeta(String key, Object value) {
        Map<String, Object> newMeta = new HashMap<>(this.meta);
        newMeta.put(key, value);
        return new ApiResponse<>(status, data, error, newMeta);
    }

    /* ===================== getters ===================== */

    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public Error getError() {
        return error;
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    /* ===================== inner types ===================== */

    public enum Status {
        SUCCESS,
        ERROR
    }

    public static final class Error {

        private final String code;
        private final String message;

        private Error(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public static Error from(ErrorCode errorCode) {
            return new Error(
                errorCode.code(),
                errorCode.message()
            );
        }

        public static Error from(ExceptionMessage message) {
            return new Error(
                message.code(),
                message.message()
            );
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
