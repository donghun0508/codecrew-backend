package site.codecrew.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;

@Embeddable
public record Email(@Column(name = "email") String address) {

    private static final String DELETE_MARKER = "-del-";

    private static final Pattern EMAIL_REGEX = Pattern.compile(
        "^[a-zA-Z0-9](?:[a-zA-Z0-9_-]*[a-zA-Z0-9]|[a-zA-Z0-9_-]*\\.[a-zA-Z0-9](?:[a-zA-Z0-9_-]*[a-zA-Z0-9])*)*@[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9]|[a-zA-Z0-9-]*\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])*)*\\.[a-zA-Z]{2,6}$"
    );

    public Email {
        if (!address.contains(DELETE_MARKER)) {
            requireValidFormat(address, "이메일 주소 형식이 잘못되었습니다.");
        }
    }

    public static Email of(String address) {
        return new Email(address);
    }

    public Email markDeleted(long timestamp) {
        String suffix = DELETE_MARKER + timestamp;
        String newAddress = this.address + suffix;

        if (newAddress.length() > 255) {
            newAddress = newAddress.substring(0, 255 - suffix.length()) + suffix;
        }

        return new Email(newAddress);
    }

    private static void requireValidFormat(String address, String errorMessage) {
        if (!EMAIL_REGEX.matcher(address).matches()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}