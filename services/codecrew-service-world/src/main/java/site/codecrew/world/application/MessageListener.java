package site.codecrew.world.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.MessageService;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageListener {

    private final MessageService messageService;

    public Mono<Void> startListening(WorldSession worldSession) {
        return worldSession.webSocketSession().receive()
            .map(WebSocketMessage::getPayloadAsText)
            .flatMap(payload -> messageService.sendChat(worldSession.sessionId(), payload))
            .onErrorResume(e -> {
                log.error("Message processing error: {}", e.getMessage());
                return Mono.empty();
            })
            .then();
    }
}