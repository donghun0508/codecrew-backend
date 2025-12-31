package site.codecrew.core.http;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumValidValidator implements ConstraintValidator<EnumValid, String> {

    private Set<String> allowed;
    private boolean ignoreCase;
    private boolean nullable;

    @Override
    public void initialize(EnumValid annotation) {
        this.ignoreCase = annotation.ignoreCase();
        this.nullable = annotation.nullable();

        this.allowed = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .map(v -> ignoreCase ? v.toLowerCase() : v)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return nullable;
        }
        String v = ignoreCase ? value.toLowerCase() : value;
        return allowed.contains(v);
    }
}