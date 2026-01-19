package site.codecrew.member.adapter.api.v1.response;


import java.time.LocalDateTime;
import site.codecrew.member.application.MemberResult;

public record MemberResponse(
    Long publicId,
    String email,
    String nickname,
    LocalDateTime createdAt
) {

    public static MemberResponse from(MemberResult result) {
        return new MemberResponse(
            result.publicId(),
            result.email(),
            result.nickname(),
            result.createdAt()
        );
    }
}
