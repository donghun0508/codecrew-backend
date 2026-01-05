package site.codecrew.world.application;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.AdditionalService;
import site.codecrew.world.domain.MessageService;
import site.codecrew.world.domain.message.InitialSyncPayload;
import site.codecrew.world.domain.message.MessageType;
import site.codecrew.world.domain.message.SenderInfo;
import site.codecrew.world.domain.message.ServerMessage;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@RequiredArgsConstructor
@Service
public class InitialSyncService {

    private final ObjectMapper objectMapper; // JSON 변환을 위해 필요

    public Mono<Void> performInitialSync(WorldSession worldSession) {
        InitialSyncPayload payload = new InitialSyncPayload(worldSession.sessionId());
        ServerMessage message = ServerMessage.of(MessageType.INITIAL_SYNC, payload);
        return Mono.fromCallable(() -> objectMapper.writeValueAsString(message))
            .flatMap(json -> {
                WebSocketSession session = worldSession.webSocketSession();
                return session.send(Mono.just(session.textMessage(json)));
            });
    }
}