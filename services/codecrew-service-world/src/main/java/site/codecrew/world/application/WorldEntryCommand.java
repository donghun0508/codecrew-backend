package site.codecrew.world.application;

import org.springframework.web.reactive.socket.WebSocketSession;

public record WorldEntryCommand(long worldId, String identityHash, WebSocketSession session) {

    public static WorldEntryCommand of(long worldId, String identityHash, WebSocketSession session) {
        return new WorldEntryCommand(worldId, identityHash, session);
    }
}
