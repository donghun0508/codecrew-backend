package site.codecrew.account.application;

public record MemberDuplicationCheckResult(boolean duplicated) {

    public static MemberDuplicationCheckResult ofDuplicated() {
        return new MemberDuplicationCheckResult(true);
    }

    public static MemberDuplicationCheckResult ofUnique() {
        return new MemberDuplicationCheckResult(false);
    }
}
