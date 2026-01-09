package site.codecrew.world.application;

import org.springframework.web.reactive.socket.WebSocketSession;
import site.codecrew.world.adapter.filter.PlayerPrincipal;

public record WorldSession(
    PlayerPrincipal playerPrincipal,
    WebSocketSession webSocketSession
) {

    public static WorldSession of(PlayerPrincipal token, WebSocketSession session) {
        return new WorldSession(token, session);
    }

    public String sessionId() {
        return webSocketSession.getId();
    }

    public long worldId() {
        return playerPrincipal.worldId();
    }

    public PlayerPrincipal credentials() {
        return playerPrincipal;
    }
}
