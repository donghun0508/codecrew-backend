package site.codecrew.world.master.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record WorldCode(@Column(unique = true, name = "code", nullable = false) String value) {

    private static final String PREFIX = "world#";
    private static final int MIN_NUM = 1;
    private static final int MAX_NUM = 10;

    public WorldCode {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("WorldId cannot be null or empty");
        }

        if (!value.startsWith(PREFIX)) {
            throw new IllegalArgumentException("WorldId must start with '" + PREFIX + "'");
        }

        try {
            String numberPart = value.substring(PREFIX.length());
            int number = Integer.parseInt(numberPart);

            if (number < MIN_NUM || number > MAX_NUM) {
                throw new IllegalArgumentException("World number must be between " + MIN_NUM + " and " + MAX_NUM);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("WorldId must contain a valid number after prefix");
        }
    }

    public static WorldCode ofNumber(int number) {
        return new WorldCode(PREFIX + number);
    }
}