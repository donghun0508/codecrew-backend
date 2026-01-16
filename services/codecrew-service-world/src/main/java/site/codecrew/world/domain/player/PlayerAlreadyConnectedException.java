package site.codecrew.world.domain.player;

import site.codecrew.world.domain.WorldErrorCode;
import site.codecrew.world.domain.WorldException;

public class PlayerAlreadyConnectedException extends WorldException {

    public PlayerAlreadyConnectedException(String identityHash) {
        super(WorldErrorCode.DUPLICATE_SESSION, "Player is already connected with an active session.", identityHash);
    }
}
