package site.codecrew.account.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;
import org.jspecify.annotations.NullMarked;

@NullMarked
@Embeddable
public record Email(@Column(name = "email", nullable = false, updatable = false) String address) {

    private static final Pattern REGEX =
        Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    public Email {
        validate(address);
    }

    private void validate(String address) {
        if(address.isBlank()) {
            throw new IllegalArgumentException("email required");
        }
        if (!REGEX.matcher(address).matches()) {
            throw new IllegalArgumentException("email invalid");
        }
    }
}
