package site.codecrew.world.master.domain;


import site.codecrew.world.master.application.exception.WorldMasterErrorCode;
import site.codecrew.world.master.application.exception.WorldMasterException;

public record ServerNode(
    String id,   // Pod Name or Unique ID
    String host   // Public IP or Service IP
) {
    public static ServerNode fromRawString(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new WorldMasterException(WorldMasterErrorCode.SERVER_NODE_INVALID_FORMAT);
        }
        String[] parts = raw.split(":");
        if (parts.length != 3) {
            throw new WorldMasterException(WorldMasterErrorCode.SERVER_NODE_INVALID_FORMAT);
        }
        return new ServerNode(parts[0], parts[1]);
    }

    public String toRawString() {
        return id + ":" + host;
    }
}