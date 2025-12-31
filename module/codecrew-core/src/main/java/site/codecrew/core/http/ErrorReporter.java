package site.codecrew.core.http;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import site.codecrew.core.exception.CoreException;
import site.codecrew.core.exception.ErrorCode;

@Slf4j
@Component
public final class ErrorReporter {

    public void send(ErrorCode code, Exception ex) {
        String location = "Unknown";
        String reason = "N/A";
        String data = "none";

        if (ex.getStackTrace().length > 0) {
            StackTraceElement caller = ex.getStackTrace()[0];
            String className = caller.getClassName();
            String simpleClassName = className.substring(className.lastIndexOf('.') + 1);
            location = String.format("%s.%s", simpleClassName, caller.getMethodName());
        }

        if (ex instanceof CoreException coreEx) {
            reason = coreEx.getReason();
            data = coreEx.getData() != null ? Arrays.deepToString(coreEx.getData()) : "none";
        }

        String logMessage = String.format("[%s] errorCode=%s, reason=%s, data=%s",
            location, code.code(), reason, data);

        switch (code.logLevel()) {
            case ERROR -> log.error(logMessage, ex);
            case WARN -> log.warn(logMessage, ex);
            case DEBUG -> log.debug(logMessage);
            default -> log.info(logMessage);
        }
    }
}