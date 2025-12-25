package site.codecrew.account.adapter.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.codecrew.account.adapter.api.v1.request.TokenIssueRequest;
import site.codecrew.account.adapter.api.v1.response.JsonWebTokenResponse.AuthenticatedJsonWebTokenResponse;
import site.codecrew.account.application.token.PlainToken;
import site.codecrew.account.application.token.TokenIssuedUseCase;
import site.codecrew.core.http.ApiResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticateController {

    private final TokenIssuedUseCase tokenIssuedUseCase;

    @PostMapping("/tokens")
    public ApiResponse<AuthenticatedJsonWebTokenResponse> issueToken(
        @RequestBody TokenIssueRequest request
    ) {
        return ApiResponse.success(
            AuthenticatedJsonWebTokenResponse.from(
                tokenIssuedUseCase.issue(PlainToken.refresh(request.refreshToken()))
            )
        );
    }
}
