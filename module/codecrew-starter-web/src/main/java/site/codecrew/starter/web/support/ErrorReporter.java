package site.codecrew.starter.web.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import site.codecrew.core.exception.ErrorCode;

@Slf4j
@Component
public final class ErrorReporter {

    public void send(ErrorCode code, Exception ex) {
        String message = "errorCode={}, message={}";

        switch (code.logLevel()) {
            case ERROR -> log.error(message, code.code(), code.message(), ex);
            case WARN -> log.warn(message, code.code(), code.message(), ex);
            default -> log.info(message, code.code(), code.message(), ex);
        }
    }
}
