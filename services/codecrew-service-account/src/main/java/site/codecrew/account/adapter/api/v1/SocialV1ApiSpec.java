package site.codecrew.account.adapter.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import site.codecrew.account.adapter.api.v1.request.SocialLoginRequest;
import site.codecrew.account.adapter.api.v1.request.SocialSignupRequest;
import site.codecrew.account.adapter.api.v1.response.JsonWebTokenResponse;
import site.codecrew.account.adapter.api.v1.response.JsonWebTokenResponse.AuthenticatedJsonWebTokenResponse;
import site.codecrew.core.http.ApiResponse;

@RequestMapping(path = "/api/v1/oauth", version = "1.0")
@Tag(name = "Social V1 API", description = "소셜 인증 API 입니다.")
public interface SocialV1ApiSpec {

    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        content = @Content(
            mediaType = "application/json",
            examples = {
                @ExampleObject(
                    name = "인증 완료 (로그인 성공)",
                    description = "기존 회원 로그인 시",
                    value = """
                {
                  "status": "SUCCESS",
                  "data": {
                    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiaWF0IjoxNTE2MjM5MDIyfQ...",
                    "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiaWF0IjoxNTE2MjM5MDIyfQ..."
                  },
                  "error": null,
                  "meta": {},
                  "timestamp": "2025-12-20T10:00:00Z"
                }
                """
                ),
                @ExampleObject(
                    name = "회원가입 대기 (신규 유저)",
                    description = "신규 소셜 유저 회원가입 필요 시",
                    value = """
                {
                  "status": "SUCCESS",
                  "data": {
                    "temporaryToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnb29nbGV8MTIzNDU2IiwiaWF0IjoxNTE2MjM5MDIyfQ..."
                  },
                  "error": null,
                  "meta": {},
                  "timestamp": "2025-12-20T10:00:00Z"
                }
                """
                )
            }
        )
    )
    @SecurityRequirements
    @PostMapping("/login")
    ApiResponse<JsonWebTokenResponse> login(
        @Valid @RequestBody SocialLoginRequest request
    );

    @Operation(
        summary = "소셜 회원가입",
        description = "임시 토큰을 사용해 소셜 회원가입을 진행하고 JWT를 발급합니다."
    )
    @PostMapping("/signup")
    ApiResponse<AuthenticatedJsonWebTokenResponse> signup(
        @Parameter(hidden = true)
        @RequestHeader("Authorization") String temporaryToken,
        @Valid @RequestBody SocialSignupRequest request
    );
}
