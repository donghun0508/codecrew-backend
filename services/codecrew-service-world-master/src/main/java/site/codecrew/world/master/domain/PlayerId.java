package site.codecrew.world.master.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;

@Embeddable
public record PlayerId(@Column(name = "public_player_id", nullable = false, updatable = false) String value) {

    public static PlayerId from(String issuer, String subject) {
        if (issuer == null || issuer.isBlank()) {
            throw new IllegalArgumentException("Issuer cannot be null or empty");
        }
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("Subject cannot be null or empty");
        }
        String rawKey = issuer + ":" + subject;
        return new PlayerId(generateHash(rawKey));
    }

    private static String generateHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new CoreException(CoreErrorCode.INTERNAL_ERROR, "Failed to generate PlayerId hash", e);
        }
    }
}