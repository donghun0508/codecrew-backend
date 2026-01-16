package site.codecrew.world.domain.world;

import site.codecrew.world.domain.WorldException;
import site.codecrew.world.domain.WorldErrorCode;

public class WorldFullException extends WorldException {

    public WorldFullException(long worldId) {
        super(WorldErrorCode.WORLD_FULL, "World capacity limit reached.", String.valueOf(worldId));
    }
}