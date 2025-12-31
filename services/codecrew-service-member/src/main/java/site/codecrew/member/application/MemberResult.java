package site.codecrew.member.application;

import site.codecrew.member.domain.Member;

public record MemberResult(
    Long publicId,
    String email,
    String nickname
) {

    public static MemberResult from(Member member) {
        return new MemberResult(
            member.publicId(),
            member.emailAddress(),
            member.nickname()
        );
    }
}
