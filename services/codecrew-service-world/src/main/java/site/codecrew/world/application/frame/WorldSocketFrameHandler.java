package site.codecrew.world.application.frame;

import org.springframework.web.reactive.socket.WebSocketMessage;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.connection.Connection;

public interface WorldSocketFrameHandler {
    Mono<Void> handle(Connection connection, WebSocketMessage message);
    WebSocketMessage.Type getSupportedType();
}
