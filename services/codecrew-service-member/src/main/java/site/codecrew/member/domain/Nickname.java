package site.codecrew.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;

@Embeddable
public record Nickname(@Column(name = "nickname", nullable = false, length = 50) String value) {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 12;
    private static final String DELETE_MARKER = "-del-";
    private static final Pattern VALID_PATTERN = Pattern.compile("^[a-zA-Z0-9가-힣]+$");

    public Nickname {
        validate(value);
    }

    public Nickname markDeleted(long timestamp) {
        String newValue = this.value + DELETE_MARKER + timestamp;

        if (newValue.length() > 50) {
            newValue = newValue.substring(0, 50);
        }

        return new Nickname(newValue);
    }

    private static void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("닉네임을 입력해주세요.");
        }

        if (value.contains(DELETE_MARKER)) {
            return;
        }

        String trimmed = value.trim();

        if (trimmed.length() < MIN_LENGTH || trimmed.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                String.format("닉네임은 %d~%d자 사이여야 합니다.", MIN_LENGTH, MAX_LENGTH)
            );
        }

        if (!VALID_PATTERN.matcher(trimmed).matches()) {
            throw new IllegalArgumentException("닉네임은 한글, 영문, 숫자만 사용할 수 있습니다.");
        }
    }
}