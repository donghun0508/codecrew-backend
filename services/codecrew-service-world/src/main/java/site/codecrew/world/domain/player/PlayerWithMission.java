package site.codecrew.world.domain.player;

import java.util.List;

public record PlayerWithMission(Player player, List<Mission> missions) {

}
