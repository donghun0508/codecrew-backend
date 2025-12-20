package site.codecrew.account.adapter.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import site.codecrew.account.adapter.api.v1.request.MemberNicknameDuplicationCheckRequest;
import site.codecrew.account.adapter.api.v1.response.MemberDuplicationCheckResponse;
import site.codecrew.account.adapter.api.v1.response.MemberResponse;
import site.codecrew.account.application.MemberUseCase;
import site.codecrew.account.application.token.PlainToken;
import site.codecrew.core.http.ApiResponse;

@RequiredArgsConstructor
@RestController
public class MemberController implements MemberV1ApiSpec {

    private final MemberUseCase memberUseCase;

    @Override
    public ApiResponse<MemberResponse> getMyProfile(String accessToken) {
        return ApiResponse.success(
            MemberResponse.from(
                memberUseCase.findByToken(PlainToken.access(accessToken))
            )
        );
    }

    @Override
    public ApiResponse<MemberDuplicationCheckResponse> duplicationCheck(
        MemberNicknameDuplicationCheckRequest request
    ) {
        return ApiResponse.success(
            MemberDuplicationCheckResponse.from(
                memberUseCase.duplicationNameCheck(request.value())
            )
        );
    }
}
