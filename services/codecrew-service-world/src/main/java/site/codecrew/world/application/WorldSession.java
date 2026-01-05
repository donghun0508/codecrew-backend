package site.codecrew.world.application;

import org.springframework.web.reactive.socket.WebSocketSession;
import site.codecrew.world.adapter.filter.EnterToken;

public record WorldSession(
    EnterToken enterToken,
    WebSocketSession webSocketSession
) {

    public static WorldSession of(EnterToken token, WebSocketSession session) {
        return new WorldSession(token, session);
    }

    public String sessionId() {
        return webSocketSession.getId();
    }

    public long worldId() {
        return enterToken.worldId();
    }

    public EnterToken credentials() {
        return enterToken;
    }
}
