package site.codecrew.member.adapter.api.v1.response;


import site.codecrew.member.application.MemberDuplicateCheckResult;

public record MemberDuplicationCheckResponse(boolean duplicated) {

    public static MemberDuplicationCheckResponse from(MemberDuplicateCheckResult result) {
        return new MemberDuplicationCheckResponse(result.duplicated());
    }
}
