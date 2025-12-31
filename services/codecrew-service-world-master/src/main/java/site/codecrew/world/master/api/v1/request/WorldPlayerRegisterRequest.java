package site.codecrew.world.master.api.v1.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WorldPlayerRegisterRequest(
    @NotBlank String worldCode,
    @NotNull Integer avatarId,
    @NotBlank String nickname
) {

}
