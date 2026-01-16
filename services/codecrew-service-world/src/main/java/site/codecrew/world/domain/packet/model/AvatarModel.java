package site.codecrew.world.domain.packet.model;

import site.codecrew.world.domain.player.Avatar;

public record AvatarModel(
    int skinId,
    int hairId,
    int hairColorId,
    int outfitId,
    int accessoryId
) {

    public static AvatarModel from(Avatar avatar) {
        return new AvatarModel(
            avatar.skinId(),
            avatar.hairId(),
            avatar.hairColorId(),
            avatar.outfitId(),
            avatar.accessoryId()
        );
    }
}
