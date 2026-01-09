package site.codecrew.world.domain;

import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

public class PlayerConnection {

    private final String sessionId;
    private final WebSocketSession webSocketSession;
    private final Player player;

    public PlayerConnection(String sessionId, WebSocketSession webSocketSession, Player player) {
        this.sessionId = sessionId;
        this.webSocketSession = webSocketSession;
        this.player = player;
    }

    public String sessionId() {
        return sessionId;
    }

    public Mono<Void> sendMessage(String payload) {
        return webSocketSession.send(Mono.just(webSocketSession.textMessage(payload))).then();
    }

    public String playerId() {
        return player.getPublicPlayerId();
    }

    public Player getPlayer() {
        return player;
    }
}

/*
{
  "type": "CHAT_MESSAGE",
  "payload": {
    "id": "msg_unique_id",
    "message": "안녕하세요!",
    "timestamp": "12:34",
    "sender": {
      "nickname": "사용자닉네임",
      "avatar": {
        "skinId": 1,
        "hairId": 2,
        "outfitId": 3,
        "accessoryId": 1
      }
    }
  }
}
 */