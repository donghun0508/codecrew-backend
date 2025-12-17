package site.codecrew.account.adapter.web;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.codecrew.account.adapter.web.request.SocialLoginRequest;
import site.codecrew.account.adapter.web.request.SocialSignupRequest;
import site.codecrew.account.adapter.web.response.JsonWebTokenResponse;
import site.codecrew.account.application.auth.SocialAuthenticateUseCase;
import site.codecrew.account.application.signup.SocialSignupUseCase;
import site.codecrew.account.application.token.JsonWebToken;
import site.codecrew.account.domain.SignupAttributes;
import site.codecrew.core.http.ApiResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/oauth", version = "1.0")
public class SocialController {

    private final SocialSignupUseCase socialSignupUseCase;
    private final SocialAuthenticateUseCase socialAuthenticateUseCase;

    @PostMapping("/login")
    public ApiResponse<JsonWebTokenResponse> authenticate(@Valid @RequestBody SocialLoginRequest request) {
        return ApiResponse.success(
            JsonWebTokenResponse.from(
                socialAuthenticateUseCase.authenticate(request.toSocialCredential())
            )
        );
    }

    @PostMapping("/signup")
    public ApiResponse<JsonWebTokenResponse> signup(
        @RequestHeader("Authorization") String temporaryToken,
        @Valid @RequestBody SocialSignupRequest request
    ) {
        return ApiResponse.success(
            JsonWebTokenResponse.from(
                socialSignupUseCase.signup(JsonWebToken.temporary(temporaryToken), new SignupAttributes(request.nickname())))
        );
    }
}
