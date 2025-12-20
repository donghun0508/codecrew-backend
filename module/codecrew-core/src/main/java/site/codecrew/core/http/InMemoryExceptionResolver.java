package site.codecrew.core.http;

import java.util.Map;
import site.codecrew.core.exception.CoreException;
import site.codecrew.core.exception.ErrorCode;

public class InMemoryExceptionResolver implements ExceptionResolver {

    private final Map<ErrorCode, ErrorResponse> mappings;

    public InMemoryExceptionResolver(Map<ErrorCode, ErrorResponse> mappings) {
        this.mappings = mappings;
    }

    @Override
    public ErrorResponse resolve(CoreException exception) {
        return mappings.getOrDefault(exception.getCode(), ErrorResponse.internalError());
    }
}
