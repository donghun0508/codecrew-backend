package site.codecrew.world.temp.domain.exception;

import site.codecrew.core.exception.CoreException;
import site.codecrew.core.exception.ErrorCode;

public class WorldException extends CoreException {

    public WorldException(WorldErrorCode code) {
        super(code);
    }

    public WorldException(WorldErrorCode code, String detailMessage) {
        super(code, detailMessage);
    }

    public WorldException(ErrorCode code, String reason, Object... data) {
        super(code, reason, data);
    }

    public WorldException(WorldErrorCode code, Throwable cause) {
        super(code, cause);
    }
}
