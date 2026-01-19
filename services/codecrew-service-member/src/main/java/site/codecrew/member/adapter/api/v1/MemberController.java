package site.codecrew.member.adapter.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import site.codecrew.core.http.ApiResponse;
import site.codecrew.member.adapter.api.v1.request.MemberDuplicationCheckRequest;
import site.codecrew.member.adapter.api.v1.request.MemberNicknameUpdateRequest;
import site.codecrew.member.adapter.api.v1.request.MemberRegisterRequest;
import site.codecrew.member.adapter.api.v1.response.MemberDuplicationCheckResponse;
import site.codecrew.member.adapter.api.v1.response.MemberResponse;
import site.codecrew.member.adapter.api.v1.response.MemberVerifyResponse;
import site.codecrew.member.application.MemberDeleteUseCase;
import site.codecrew.member.application.MemberDuplicateCheckCommand;
import site.codecrew.member.application.MemberDuplicateCheckUseCase;
import site.codecrew.member.application.MemberNicknameUpdateCommand;
import site.codecrew.member.application.MemberNicknameUpdateUseCase;
import site.codecrew.member.application.MemberProfileQueryUseCase;
import site.codecrew.member.application.MemberRegisterCommand;
import site.codecrew.member.application.MemberRegisterUseCase;
import site.codecrew.member.application.MemberVerifyUseCase;
import site.codecrew.member.domain.IssuerSubjectIdentity;
import site.codecrew.member.domain.Nickname;
import site.codecrew.starter.web.argumentresolver.AuthenticatedUser;

@RequiredArgsConstructor
@RestController
class MemberController implements MemberV1ApiSpec {

    private final MemberVerifyUseCase memberVerifyUseCase;
    private final MemberDuplicateCheckUseCase memberDuplicateCheckUseCase;
    private final MemberNicknameUpdateUseCase memberNicknameUpdateUseCase;
    private final MemberDeleteUseCase memberDeleteUseCase;
    private final MemberProfileQueryUseCase memberProfileQueryUseCase;
    private final MemberRegisterUseCase memberRegisterUseCase;

    @Override
    public ApiResponse<MemberVerifyResponse> verify(AuthenticatedUser authenticatedUser) {
        return ApiResponse.success(
            MemberVerifyResponse.from(
                memberVerifyUseCase.verify(new IssuerSubjectIdentity(authenticatedUser.issuer(), authenticatedUser.subject()))
            )
        );
    }

    @Override
    public ApiResponse<MemberDuplicationCheckResponse> duplicationCheck(MemberDuplicationCheckRequest request) {
        return ApiResponse.success(
            MemberDuplicationCheckResponse.from(
                memberDuplicateCheckUseCase.check(new MemberDuplicateCheckCommand(request.type(), request.value()))
            )
        );
    }

    @Override
    public ApiResponse<Void> updateNickname(AuthenticatedUser authenticatedUser, MemberNicknameUpdateRequest request) {
        memberNicknameUpdateUseCase.updateNickname(new MemberNicknameUpdateCommand(
            new IssuerSubjectIdentity(authenticatedUser.issuer(), authenticatedUser.subject()), new Nickname(request.nickname())
        ));
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<Void> delete(AuthenticatedUser authenticatedUser) {
        memberDeleteUseCase.delete(new IssuerSubjectIdentity(authenticatedUser.issuer(), authenticatedUser.subject()));
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<MemberResponse> getMyProfile(AuthenticatedUser authenticatedUser) {
        return ApiResponse.success(
            MemberResponse.from(
                memberProfileQueryUseCase.getMyProfile(new IssuerSubjectIdentity(authenticatedUser.issuer(), authenticatedUser.subject()))
            )
        );
    }

    @Override
    public ApiResponse<Void> registerMember(AuthenticatedUser authenticatedUser, MemberRegisterRequest request) {
        memberRegisterUseCase.register(new MemberRegisterCommand(
            authenticatedUser.issuer(),
            authenticatedUser.subject(),
            authenticatedUser.email(),
            request.nickname()
        ));
        return ApiResponse.success();
    }
}
