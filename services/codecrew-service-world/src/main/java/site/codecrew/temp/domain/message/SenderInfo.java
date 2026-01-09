package site.codecrew.world.domain.message;

import site.codecrew.world.domain.Avatar;
import site.codecrew.world.domain.Player;

public record SenderInfo(
    String nickname,
    Avatar avatar
) {
    public static SenderInfo from(Player player) {
        return new SenderInfo(player.getAttribute().nickname(), player.getAvatar());
    }
}