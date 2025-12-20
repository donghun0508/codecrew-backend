package site.codecrew.account.adapter.api.v1.response;


import site.codecrew.account.application.MemberResult;

public record MemberResponse(
    Long publicId,
    String email,
    String name,
    String nickname,
    String profileImageUrl
) {

    public static MemberResponse from(MemberResult result) {
        return new MemberResponse(
            result.publicId(),
            result.email(),
            result.name(),
            result.nickname(),
            result.profileImageUrl()
        );
    }
}
