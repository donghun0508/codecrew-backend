package site.codecrew.world.application;

import org.springframework.web.reactive.socket.WebSocketSession;
import site.codecrew.world.domain.Player;

public record Connection(
    Player player,
    WorldSession worldSession
) {

    public static Connection from(Player player, WorldSession worldSession) {
        return new Connection(player, worldSession);
    }

    public WebSocketSession webSocketSession() {
        return worldSession.webSocketSession();
    }
}
