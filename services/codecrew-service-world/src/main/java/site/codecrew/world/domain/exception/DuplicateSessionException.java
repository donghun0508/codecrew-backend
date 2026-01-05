package site.codecrew.world.domain.exception;

public class DuplicateSessionException extends WorldException {

    public DuplicateSessionException(String playerId) {
        super(WorldErrorCode.DUPLICATE_SESSION, "Player is already connected with an active session.", playerId);
    }
}
