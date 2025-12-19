package site.codecrew.account.adapter.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.codecrew.account.adapter.web.request.MemberNicknameDuplicationCheckRequest;
import site.codecrew.account.adapter.web.response.MemberDuplicationCheckResponse;
import site.codecrew.account.adapter.web.response.MemberResponse;
import site.codecrew.account.application.MemberUseCase;
import site.codecrew.account.application.token.JsonWebToken;
import site.codecrew.core.http.ApiResponse;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/members", version = "1.0")
public class MemberController {

    private final MemberUseCase memberUseCase;

    @GetMapping("/me")
    public ApiResponse<MemberResponse> verifyMember(@RequestHeader("Authorization") String accessToken) {
        return ApiResponse.success(
            MemberResponse.from(
                memberUseCase.findByToken(JsonWebToken.access(accessToken))
            )
        );
    }

    @PostMapping("/duplication-check")
    public ApiResponse<MemberDuplicationCheckResponse> duplicationCheck(
        @Valid @RequestBody MemberNicknameDuplicationCheckRequest request
    ) {
        return ApiResponse.success(
            MemberDuplicationCheckResponse.from(
                memberUseCase.duplicationNameCheck(request.value())
            )
        );
    }
}
