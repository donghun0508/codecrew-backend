package site.codecrew.world.domain.packet.model;

import site.codecrew.world.domain.player.PlayerAttribute;

public record AttributeModel(
    String nickname,
    String statusMessage
) {

    public static AttributeModel from(PlayerAttribute attribute) {
        return new AttributeModel(
            attribute.nickname(),
            attribute.statusMessage()
        );
    }
}
