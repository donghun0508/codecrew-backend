package site.codecrew.account.adapter.web.response;


import site.codecrew.account.application.MemberDuplicationCheckResult;

public record MemberDuplicationCheckResponse(boolean duplicated) {

    public static MemberDuplicationCheckResponse from(MemberDuplicationCheckResult result) {
        return new MemberDuplicationCheckResponse(result.duplicated());
    }
}
