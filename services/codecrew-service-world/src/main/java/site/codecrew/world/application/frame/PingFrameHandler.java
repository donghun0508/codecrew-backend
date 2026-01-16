package site.codecrew.world.application.frame;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketMessage.Type;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.connection.Connection;

@Component
class PingFrameHandler implements WorldSocketFrameHandler {

    @Override
    public Mono<Void> handle(Connection connection, WebSocketMessage message) {
        connection.updateHeartbeat();
        return connection.getSocket()
            .send(Mono.just(connection.getSocket().pongMessage(dataBufferFactory -> message.getPayload())));
    }

    @Override
    public Type getSupportedType() {
        return Type.PING;
    }

}
