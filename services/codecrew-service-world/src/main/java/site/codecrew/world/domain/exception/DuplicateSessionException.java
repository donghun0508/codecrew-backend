package site.codecrew.world.domain.exception;

public class DuplicateSessionException extends WorldException {

    public DuplicateSessionException(String identityHash) {
        super(WorldErrorCode.DUPLICATE_SESSION, "Player is already connected with an active session.", identityHash);
    }
}
