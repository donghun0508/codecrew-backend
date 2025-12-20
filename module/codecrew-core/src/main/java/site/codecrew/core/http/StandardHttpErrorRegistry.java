package site.codecrew.core.http;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.ErrorCode;

public final class StandardHttpErrorRegistry {

    private StandardHttpErrorRegistry() {
    }

    public static Map<ErrorCode, ErrorResponse> defaultMappings() {
        return Arrays.stream(CoreErrorCode.values())
            .collect(Collectors.toUnmodifiableMap(Function.identity(), ErrorResponse::from)
            );
    }

}