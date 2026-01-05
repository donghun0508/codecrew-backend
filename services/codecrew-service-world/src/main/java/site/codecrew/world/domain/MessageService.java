package site.codecrew.world.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.message.ChatPayload;
import site.codecrew.world.domain.message.ClientChatRequest;
import site.codecrew.world.domain.message.MessageType;
import site.codecrew.world.domain.message.SenderInfo;
import site.codecrew.world.domain.message.ServerMessage;
import site.codecrew.world.domain.repository.SessionRoutingRepository;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final SessionRoutingRepository sessionRoutingRepository;
    private final PlayerConnectionService playerConnectionService; // 또는 ConnectionRegistry
    private final ObjectMapper objectMapper;

    public Mono<Void> sendDirect(String targetSessionId, ServerMessage<?> messageObj) {
        return Mono.fromCallable(() -> objectMapper.writeValueAsString(messageObj))
            .flatMap(jsonPayload -> sendMessageToSession(targetSessionId, jsonPayload));
    }

    public Mono<Void> sendChat(String senderSessionId, String rawJson) {
        return Mono.fromCallable(() ->
                objectMapper.readValue(rawJson, ClientChatRequest.class)
            )
            .flatMap(request -> {
                String text = request.message();
                if (text == null || text.isBlank()) {
                    return Mono.empty();
                }
                return playerConnectionService.getConnection(senderSessionId)
                    .flatMap(connection -> {
                        ChatPayload chatPayload = new ChatPayload(text, SenderInfo.from(connection.getPlayer()));
                        ServerMessage message = ServerMessage.of(MessageType.CHAT, connection.sessionId(), chatPayload);
                        return broadcast(senderSessionId, message);
                    });
            })
            .onErrorResume(e -> {
                log.warn("Invalid chat request from {}: {}", senderSessionId, e.getMessage());
                return Mono.empty();
            });
    }

    public Mono<Void> broadcast(String senderSessionId, ServerMessage<?> messageObj) {
        return Mono.fromCallable(() -> objectMapper.writeValueAsString(messageObj))
            .flatMap(jsonString -> executeBroadcast(senderSessionId, jsonString));
    }

    private Mono<Void> executeBroadcast(String senderSessionId, String jsonPayload) {
        return sessionRoutingRepository.findById(senderSessionId)
            .flatMap(routing -> {
                long currentMapId = routing.routingKey();

                return sessionRoutingRepository.findSessionIdsByRoutingKey(currentMapId)
                    .flatMap(targetId -> sendMessageToSession(targetId, jsonPayload))
                    .then();
            });
    }


    private Mono<Void> sendMessageToSession(String targetSessionId, String payload) {
        return playerConnectionService.getConnection(targetSessionId)
            .flatMap(connection -> connection.sendMessage(payload))
            .onErrorResume(e -> {
                log.debug("Failed to send message to target {}: {}", targetSessionId, e.getMessage());
                return Mono.empty();
            });
    }
}