package site.codecrew.account.application;

import site.codecrew.account.domain.Member;

public record MemberResult(
    Long publicId,
    String email,
    String name,
    String nickname,
    String profileImageUrl
) {

    public static MemberResult from(Member member) {
        return new MemberResult(
            member.publicId(),
            member.email(),
            member.name(),
            member.nickname(),
            member.profileImageUrl()
        );
    }
}