package site.codecrew.world.master.adapter.web.api.v1.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import site.codecrew.world.master.domain.Avatar;
import site.codecrew.world.master.domain.PlayerAttribute;

public record WorldPlayerRegisterRequest(
    @NotBlank String worldCode,
    @NotNull AvatarSpec avatar,
    @NotNull ProfileSpec profile
) {
    public record AvatarSpec(
        @NotNull Integer hairColorId,
        @NotNull Integer hairId,
        @NotNull Integer outfitId,
        @NotNull Integer skinId,
        @NotNull Integer accessoryId
    ) {

        public Avatar toDomain() {
            return Avatar.of(skinId, hairId, hairColorId, outfitId, accessoryId);
        }
    }

    public record ProfileSpec(
        @NotBlank String nickname,
        @NotBlank String statusMessage
    ) {

        public PlayerAttribute toDomain() {
            return PlayerAttribute.of(nickname, statusMessage);
        }
    }
}

