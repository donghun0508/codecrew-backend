package site.codecrew.member.application;

public record MemberRegisterCommand(
    String issuer,
    String subject,
    String emailAddress,
    String nickname
) {

}
