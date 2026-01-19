package site.codecrew.world.master.adapter.web.api.v1;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import site.codecrew.core.http.ApiResponse;
import site.codecrew.starter.web.argumentresolver.AuthenticatedUser;
import site.codecrew.starter.web.argumentresolver.AuthenticatedUserPrincipal;
import site.codecrew.world.master.adapter.web.api.v1.request.WorldEnterRequest;
import site.codecrew.world.master.adapter.web.api.v1.request.WorldPlayerRegisterRequest;
import site.codecrew.world.master.adapter.web.api.v1.response.WorldEnterResponse;

@RequestMapping("/api/v1/world-masters")
@Tag(name = "World Master Player V1 Api", description = "월드 마스터 관련 API")
public interface WorldMasterPlayerV1ApiSpec {

    @Operation(
        summary = "월드 배정 요청",
        description = "인증된 월드 마스터에게 운영할 월드를 배정합니다."
    )
    @PostMapping(value = "/assignments", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<WorldEnterResponse> enter(
        @AuthenticatedUserPrincipal AuthenticatedUser authenticatedUser, @Valid @RequestBody WorldEnterRequest request
    );

    @Operation(
        summary = "월드 플레이어 등록",
        description = "월드에서 플레이할 플레이어를 등록합니다."
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/players", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<Void> register(
        @AuthenticatedUserPrincipal AuthenticatedUser authenticatedUser,
        @Valid @RequestBody WorldPlayerRegisterRequest request
    );
}
