package site.codecrew.core.http;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.ErrorCode;

public final class StandardHttpErrorRegistry {

    private StandardHttpErrorRegistry() {
    }

    public static Map<ErrorCode, ErrorResponse> defaultMappings() {
        Map<ErrorCode, ErrorResponse> mappings = new HashMap<>();

        try (ScanResult scanResult = new ClassGraph()
            .enableAllInfo()
            .acceptPackages("site.codecrew")
            .scan()) {

            for (ClassInfo classInfo : scanResult.getClassesImplementing(ErrorCode.class)) {
                if (classInfo.isEnum()) {
                    Class<?> clazz = classInfo.loadClass();
                    Object[] constants = clazz.getEnumConstants();

                    for (Object constant : constants) {
                        ErrorCode errorCode = (ErrorCode) constant;
                        mappings.put(errorCode, ErrorResponse.from(errorCode));
                    }
                }
            }
        }

        return Collections.unmodifiableMap(mappings);
    }

}