package site.codecrew.member.adapter.api.v1.response;

import site.codecrew.member.application.MemberVerifyResult;

public record MemberVerifyResponse(
    boolean registered
) {

    public static MemberVerifyResponse from(MemberVerifyResult result) {
        return new MemberVerifyResponse(result.registered());
    }
}
