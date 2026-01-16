package site.codecrew.world.application;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketMessage.Type;
import reactor.core.publisher.Mono;
import site.codecrew.world.application.frame.WorldSocketFrameHandler;
import site.codecrew.world.domain.connection.Connection;

@Slf4j
@Component
public class WorldInboundMessageHandler {

    private final Map<Type, WorldSocketFrameHandler> handlers;

    public WorldInboundMessageHandler(List<WorldSocketFrameHandler> handlerList) {
        this.handlers = handlerList.stream()
            .collect(Collectors.toMap(WorldSocketFrameHandler::getSupportedType, Function.identity()));
    }

    public Mono<Void> handle(Connection connection) {
        return connection.getSocket().receive()
            .flatMap(message -> dispatch(connection, message))
            .doOnError(e -> log.error("Session Error: {}", e.getMessage()))
            .then();
    }

    private Mono<Void> dispatch(Connection connection, WebSocketMessage message) {
        WorldSocketFrameHandler handler = handlers.get(message.getType());

        if (handler == null) {
            return Mono.empty();
        }
        return handler.handle(connection, message);
    }

}
