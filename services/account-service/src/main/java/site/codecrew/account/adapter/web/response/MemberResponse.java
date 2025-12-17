package site.codecrew.account.adapter.web.response;


import site.codecrew.account.application.MemberResult;

public record MemberResponse(
    Long id,
    String email,
    String name,
    String nickname,
    String profileImageUrl
) {

    public static MemberResponse from(MemberResult result) {
        return new MemberResponse(
            result.id(),
            result.email(),
            result.name(),
            result.nickname(),
            result.profileImageUrl()
        );
    }
}
