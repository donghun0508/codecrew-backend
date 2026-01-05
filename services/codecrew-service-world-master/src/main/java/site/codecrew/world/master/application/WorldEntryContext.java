package site.codecrew.world.master.application;

import site.codecrew.world.master.domain.Player;
import site.codecrew.world.master.domain.PlayerId;
import site.codecrew.world.master.domain.World;

public record WorldEntryContext(
    World world,
    Player player
) {

    public Long worldId() {
        return world.getId();
    }


    public PlayerId playerId() {
        return player.getPlayerId();
    }
}