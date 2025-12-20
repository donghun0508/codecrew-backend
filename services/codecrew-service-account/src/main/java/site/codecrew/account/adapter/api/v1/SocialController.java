package site.codecrew.account.adapter.api.v1;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import site.codecrew.account.adapter.api.v1.request.SocialLoginRequest;
import site.codecrew.account.adapter.api.v1.request.SocialSignupRequest;
import site.codecrew.account.adapter.api.v1.response.JsonWebTokenResponse;
import site.codecrew.account.adapter.api.v1.response.JsonWebTokenResponse.AuthenticatedJsonWebTokenResponse;
import site.codecrew.account.application.auth.SocialAuthenticateUseCase;
import site.codecrew.account.application.signup.SocialSignupUseCase;
import site.codecrew.account.application.token.JsonWebToken;
import site.codecrew.account.application.token.PlainToken;
import site.codecrew.account.domain.SignupAttributes;
import site.codecrew.core.http.ApiResponse;

@RequiredArgsConstructor
@RestController
public class SocialController implements SocialV1ApiSpec {

    private final SocialSignupUseCase socialSignupUseCase;
    private final SocialAuthenticateUseCase socialAuthenticateUseCase;

    @Override
    public ApiResponse<JsonWebTokenResponse> login(SocialLoginRequest request) {
        return ApiResponse.success(
            JsonWebTokenResponse.from(
                socialAuthenticateUseCase.authenticate(request.toSocialCredential())
            )
        );
    }

    @Override
    public ApiResponse<AuthenticatedJsonWebTokenResponse> signup(String temporaryToken,
        SocialSignupRequest request) {
        return ApiResponse.success(
            AuthenticatedJsonWebTokenResponse.from(
                socialSignupUseCase.signup(PlainToken.temporary(temporaryToken), new SignupAttributes(request.nickname())))
        );
    }
}
