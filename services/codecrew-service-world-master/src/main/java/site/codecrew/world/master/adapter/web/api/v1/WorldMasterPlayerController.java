package site.codecrew.world.master.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import site.codecrew.core.http.ApiResponse;
import site.codecrew.starter.web.argumentresolver.AuthenticatedUser;
import site.codecrew.world.master.api.v1.request.WorldEnterRequest;
import site.codecrew.world.master.api.v1.request.WorldPlayerRegisterRequest;
import site.codecrew.world.master.api.v1.response.WorldEnterResponse;
import site.codecrew.world.master.application.WorldPlayerPlayerRegisterCommand;
import site.codecrew.world.master.application.WorldPlayerAvatarRegisterUseCase;
import site.codecrew.world.master.application.WorldEntryUseCase;
import site.codecrew.world.master.domain.WorldMember;

@RequiredArgsConstructor
@RestController
class WorldMasterPlayerController implements WorldMasterPlayerV1ApiSpec {

    private final WorldEntryUseCase worldEntryUseCase;
    private final WorldPlayerAvatarRegisterUseCase worldPlayerAvatarRegisterUseCase;

    @Override
    public ApiResponse<WorldEnterResponse> enter(AuthenticatedUser authenticatedUser, WorldEnterRequest request) {
        return ApiResponse.success(
            WorldEnterResponse.from(
                worldEntryUseCase.enter(
                    WorldMember.of(request.worldCode(), authenticatedUser.issuer(), authenticatedUser.subject())
                )
            )
        );
    }

    @Override
    public ApiResponse<Void> register(AuthenticatedUser authenticatedUser, WorldPlayerRegisterRequest request) {
        worldPlayerAvatarRegisterUseCase.register(new WorldPlayerPlayerRegisterCommand(
            WorldMember.of(request.worldCode(), authenticatedUser.issuer(), authenticatedUser.subject()),
            request.avatar().toDomain(),
            request.profile().toDomain()
        ));
        return ApiResponse.success();
    }
}
