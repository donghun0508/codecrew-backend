package site.codecrew.world.domain.player;

import site.codecrew.world.domain.WorldErrorCode;
import site.codecrew.world.domain.WorldException;

public class DuplicatePlayerLoginException extends WorldException {

    public DuplicatePlayerLoginException(String identityHash) {
        super(WorldErrorCode.DUPLICATE_SESSION, "Player is already connected with an active session.", identityHash);
    }
}
