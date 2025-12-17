package site.codecrew.core.exception;

public interface ErrorCode {

    int httpStatus();
    String code();
    String message();
    LogLevel logLevel();
}