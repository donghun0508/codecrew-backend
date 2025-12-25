package site.codecrew.core.exception;

public class CoreException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final transient ErrorCode code;
    private final String message;
    private final Throwable cause;

    public CoreException(ErrorCode code) {
        this(code, null, null);
    }

    public CoreException(ErrorCode code, String message) {
        this(code, message, null);
    }

    public CoreException(ErrorCode code, Throwable cause) {
        this(code, null, cause);
    }

    public CoreException(ErrorCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
        this.cause = cause;
    }

    public ErrorCode getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
