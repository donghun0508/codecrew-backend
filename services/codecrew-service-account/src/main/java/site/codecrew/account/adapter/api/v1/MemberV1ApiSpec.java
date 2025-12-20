package site.codecrew.account.adapter.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import site.codecrew.account.adapter.api.v1.request.MemberNicknameDuplicationCheckRequest;
import site.codecrew.account.adapter.api.v1.response.MemberDuplicationCheckResponse;
import site.codecrew.account.adapter.api.v1.response.MemberResponse;
import site.codecrew.core.http.ApiResponse;

@RequestMapping(path = "/api/v1/members", version = "1.0")
@Tag(name = "Member V1 API", description = "멤버 관리 API 입니다.")
public interface MemberV1ApiSpec {

    @Operation(
        summary = "내 프로필 조회",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/me")
    ApiResponse<MemberResponse> getMyProfile(
        @Parameter(hidden = true)
        @RequestHeader(value = "Authorization") String authorization
    );

    @Operation(
        summary = "닉네임 중복 체크",
        description = "멤버 닉네임의 중복 여부를 확인합니다."
    )
    @PostMapping("/duplication-check")
    ApiResponse<MemberDuplicationCheckResponse> duplicationCheck(@RequestBody MemberNicknameDuplicationCheckRequest request);
}
