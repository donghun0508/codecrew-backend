package site.codecrew.member.adapter.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import site.codecrew.core.http.ApiResponse;
import site.codecrew.member.adapter.api.v1.request.MemberDuplicationCheckRequest;
import site.codecrew.member.adapter.api.v1.request.MemberNicknameUpdateRequest;
import site.codecrew.member.adapter.api.v1.request.MemberRegisterRequest;
import site.codecrew.member.adapter.api.v1.response.MemberDuplicationCheckResponse;
import site.codecrew.member.adapter.api.v1.response.MemberResponse;
import site.codecrew.member.adapter.api.v1.response.MemberVerifyResponse;
import site.codecrew.starter.web.argumentresolver.AuthenticatedUser;
import site.codecrew.starter.web.argumentresolver.AuthenticatedUserPrincipal;

@RequestMapping(path = "/api/v1/members", version = "1.0")
@Tag(name = "Member V1 API", description = "멤버 관리 API 입니다.")
public interface MemberV1ApiSpec {

    @Operation(summary = "회원 가입 여부 조회")
    @GetMapping(value = "/verify", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<MemberVerifyResponse> verify(@AuthenticatedUserPrincipal AuthenticatedUser authenticatedUser);

    @Operation(summary = "내 프로필 조회")
    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<MemberResponse> getMyProfile(@AuthenticatedUserPrincipal AuthenticatedUser authenticatedUser);

    @Operation(summary = "닉네임 중복 체크", description = "멤버 닉네임의 중복 여부를 확인합니다.")
    @PostMapping(value = "/duplication-check", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<MemberDuplicationCheckResponse> duplicationCheck(@Valid @RequestBody MemberDuplicationCheckRequest request);

    @Operation(summary = "닉네임 수정", description = "멤버 닉네임을 수정합니다.")
    @PatchMapping(value = "/nickname", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<Void> updateNickname(@AuthenticatedUserPrincipal AuthenticatedUser authenticatedUser, @Valid @RequestBody MemberNicknameUpdateRequest request);

    @Operation(summary = "멤버 삭제", description = "멤버를 삭제합니다.")
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<Void> delete(@AuthenticatedUserPrincipal AuthenticatedUser authenticatedUser);

    @Operation(summary = "멤버 가입", description = "새로운 멤버를 가입시킵니다.")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<Void> registerMember(@AuthenticatedUserPrincipal AuthenticatedUser authenticatedUser, @Valid @RequestBody MemberRegisterRequest request);
}
