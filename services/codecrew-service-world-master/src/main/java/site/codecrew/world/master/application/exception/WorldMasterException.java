package site.codecrew.world.master.application.exception;

import site.codecrew.core.exception.CoreException;

public class WorldMasterException extends CoreException {

    public WorldMasterException(WorldMasterErrorCode code) {
        super(code);
    }

    public WorldMasterException(WorldMasterErrorCode code, String detailMessage) {
        super(code, detailMessage);
    }

    public WorldMasterException(WorldMasterErrorCode code, Throwable cause) {
        super(code, cause);
    }
}
