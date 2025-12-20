package site.codecrew.account.application.token;

public enum ClaimKey {
    HEADER_TYPE("JWT"),
    SUB("sub"),
    EMAIL("email"),
    NAME("name"),
    PICTURE("picture"),
    SOCIAL_TYPE("socialType"),
    TYPE("type");

    private final String key;

    ClaimKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}