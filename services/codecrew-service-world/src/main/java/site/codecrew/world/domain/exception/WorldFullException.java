package site.codecrew.world.domain.exception;

public class WorldFullException extends WorldException {

    public WorldFullException(long worldId) {
        super(WorldErrorCode.WORLD_FULL, "World capacity limit reached.", String.valueOf(worldId));
    }
}