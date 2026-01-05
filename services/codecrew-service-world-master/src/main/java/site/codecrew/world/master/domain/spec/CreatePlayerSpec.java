package site.codecrew.world.master.domain.spec;

import site.codecrew.world.master.domain.Avatar;
import site.codecrew.world.master.domain.PlayerAttribute;
import site.codecrew.world.master.domain.PlayerId;

public interface CreatePlayerSpec {

    PlayerId playerId();
    Avatar avatar();
    PlayerAttribute attribute();

    default void validate() {
        if (playerId() == null) {
            throw new IllegalArgumentException("playerId is required");
        }
    }
}