package site.codecrew.account.application;

import site.codecrew.account.domain.Member;

public record MemberResult(
    Long id,
    String email,
    String name,
    String nickname,
    String profileImageUrl
) {

    public static MemberResult from(Member member) {
        return new MemberResult(
            member.id(),
            member.email(),
            member.name(),
            member.nickname(),
            member.profileImageUrl()
        );
    }
}