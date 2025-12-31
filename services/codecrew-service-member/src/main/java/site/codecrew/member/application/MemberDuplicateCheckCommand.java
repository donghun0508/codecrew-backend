package site.codecrew.member.application;


public record MemberDuplicateCheckCommand(Type type, String value) {

    public enum Type {
        NICKNAME
    }
}
