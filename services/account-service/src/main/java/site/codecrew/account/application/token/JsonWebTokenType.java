package site.codecrew.account.application.token;

public enum JsonWebTokenType {
    ACCESS("access"),
    TEMPORARY("temporary");

    private final String value;

    JsonWebTokenType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}