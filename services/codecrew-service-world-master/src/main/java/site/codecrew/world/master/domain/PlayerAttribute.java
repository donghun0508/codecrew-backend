package site.codecrew.world.master.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.springframework.util.StringUtils;

@Embeddable
public record PlayerAttribute(
    @Column(name = "nickname", nullable = false) String nickname,
    @Column(name = "status_message") String statusMessage
) {

    public static PlayerAttribute of(String nickname, String statusMessage) {
        return new PlayerAttribute(nickname, StringUtils.hasText(statusMessage) ? statusMessage : null);
    }

    public void validate() {
        requireNotBlank("nickname", nickname);
        requireMaxLength("nickname", nickname, 10);

        if (statusMessage != null) {
            requireMaxLength("statusMessage", statusMessage, 10);
        }
    }

    private static void requireNotBlank(String field, String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
    }

    private static void requireMaxLength(String field, String value, int max) {
        if (value.length() > max) {
            throw new IllegalArgumentException(
                field + " must be at most " + max + " characters (actual: " + value.length() + ")"
            );
        }
    }
}
