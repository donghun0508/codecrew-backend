package site.codecrew.core.exception;

import lombok.Getter;
import java.util.Arrays;

@Getter
public class CoreException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final transient ErrorCode code;
    private final String reason;
    private final Object[] data;

    public CoreException(ErrorCode code) {
        this(code, code.message(), (Object[]) null);
    }

    public CoreException(ErrorCode code, String reason, Object... data) {
        super(String.format("[%s] %s", code.code(), reason));
        this.code = code;
        this.reason = reason;
        this.data = data;
    }

    public CoreException(ErrorCode code, Throwable cause) {
        super(code.message(), cause);
        this.code = code;
        this.reason = code.message();
        this.data = null;
    }
}