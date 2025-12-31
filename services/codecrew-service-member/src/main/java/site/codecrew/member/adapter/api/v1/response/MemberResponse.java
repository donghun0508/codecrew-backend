package site.codecrew.member.adapter.api.v1.response;


import site.codecrew.member.application.MemberResult;

public record MemberResponse(
    Long publicId,
    String email,
    String name,
    String nickname
) {

    public static MemberResponse from(MemberResult result) {
        return null;
    }
}
