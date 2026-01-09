package site.codecrew.world.master.domain.spec;

import site.codecrew.world.master.domain.Avatar;
import site.codecrew.world.master.domain.IdentityHash;
import site.codecrew.world.master.domain.PlayerAttribute;

public interface CreatePlayerSpec {

    IdentityHash playerId();
    Avatar avatar();
    PlayerAttribute attribute();

    default void validate() {
        if (playerId() == null) {
            throw new IllegalArgumentException("playerId is required");
        }
        if(avatar() == null) {
            throw new IllegalArgumentException("avatar is required");
        }
        if(attribute() == null) {
            throw new IllegalArgumentException("attribute is required");
        }
        avatar().validate();
        attribute().validate();
    }
}