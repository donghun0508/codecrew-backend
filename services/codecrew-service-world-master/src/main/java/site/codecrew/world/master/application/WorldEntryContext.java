package site.codecrew.world.master.application;

import site.codecrew.world.master.domain.Player;
import site.codecrew.world.master.domain.IdentityHash;
import site.codecrew.world.master.domain.World;

public record WorldEntryContext(
    World world,
    Player player
) {

    public Long worldId() {
        return world.getId();
    }


    public IdentityHash playerId() {
        return player.getIdentityHash();
    }
}