package site.codecrew.member.application;

import java.time.LocalDateTime;
import site.codecrew.member.domain.Member;

public record MemberResult(
    Long publicId,
    String email,
    String nickname,
    LocalDateTime createdAt
) {

    public static MemberResult from(Member member) {
        return new MemberResult(
            member.publicId(),
            member.emailAddress(),
            member.nickname().value(),
            member.getCreatedAt().toLocalDateTime()
        );
    }
}
