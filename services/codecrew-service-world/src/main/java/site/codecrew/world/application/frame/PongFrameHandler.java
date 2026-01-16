package site.codecrew.world.application.frame;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketMessage.Type;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.connection.Connection;

@Slf4j
@Component
class PongFrameHandler implements WorldSocketFrameHandler {

    @Override
    public Type getSupportedType() {
        return Type.PONG;
    }

    @Override
    public Mono<Void> handle(Connection connection, WebSocketMessage message) {
        connection.updateHeartbeat();
        return Mono.empty();
    }

}