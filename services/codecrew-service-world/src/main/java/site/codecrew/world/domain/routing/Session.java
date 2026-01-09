package site.codecrew.world.domain.routing;

import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

public record Session(WebSocketSession webSocketSession) {

    public String id() {
        return webSocketSession.getId();
    }

    public Mono<Void> send(String message) {
        return webSocketSession.send(Mono.just(webSocketSession.textMessage(message)));
    }
}