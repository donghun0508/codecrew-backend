package site.codecrew.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public final class ErrorReporter {

    public void send(ErrorCode code, Exception ex) {
        String message = "errorCode={}, message={}";

        String errorMessage = code.detailMessage() == null ? code.message()
            : code.message() + ", " + code.detailMessage();
        switch (code.logLevel()) {
            case ERROR -> log.error(message, code.code(), errorMessage, ex);
            case WARN -> log.warn(message, code.code(), errorMessage, ex);
            default -> log.info(message, code.code(), errorMessage, ex);
        }
    }
}
