package site.codecrew.world.domain.packet.model;

import site.codecrew.world.domain.player.Player;

public record PlayerSimpleModel(
    String playerId,
    AvatarModel avatar,
    AttributeModel attribute
) {

    public static PlayerSimpleModel from(Player player) {
        return new PlayerSimpleModel(
            player.getIdentityHash(),
            AvatarModel.from(player.getAvatar()),
            AttributeModel.from(player.getAttribute())
        );
    }
}
