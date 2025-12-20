package site.codecrew.core.http;

import site.codecrew.core.exception.CoreException;

public interface ExceptionResolver {
    ErrorResponse resolve(CoreException exception);
}
