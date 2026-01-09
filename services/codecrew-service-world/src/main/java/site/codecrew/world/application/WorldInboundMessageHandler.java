package site.codecrew.world.application;

import static site.codecrew.world.domain.routing.MessagePublisherType.PRIVATE_AREA;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.WorldSessionContext;
import site.codecrew.world.domain.packet.PacketSender;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.ServerPacket;
import site.codecrew.world.domain.packet.payload.ChatPayload;
import site.codecrew.world.domain.packet.payload.client.ChatSendPayload;
import site.codecrew.world.domain.routing.MessagePublisherType;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@RequiredArgsConstructor
@Component
public class WorldInboundMessageHandler {

    private final ObjectMapper objectMapper;
    private final PacketSender packetSender;

    public Mono<Void> start(WorldSessionContext context) {
        return context.session().webSocketSession().receive()
            .map(WebSocketMessage::getPayloadAsText)
            .flatMap(text -> handleChat(context, text))
            .onErrorResume(e -> {
                log.error("Message processing error: {}", e.getMessage(), e);
                return Mono.empty();
            })
            .then();
    }

    private Mono<Void> handleChat(WorldSessionContext context, String text) {
        return Mono.fromCallable(() -> objectMapper.readValue(text, ChatSendPayload.class))
            .flatMap(req -> {
                // 1) 서버 전파용 payload로 변환
                ChatPayload payload = new ChatPayload(
                    req.message(),
                    ChatPayload.SenderInfo.from(context.player()) // context에서 Player 꺼내는 메서드에 맞게 수정
                );

                // 2) packet 생성 (S->C notify)
                ServerPacket<ChatPayload> packet =
                    ServerPacket.from(PacketType.C_NET_CHAT_MESSAGE_NOTIFY, context.sessionId(), payload);

                // 3) 브로드캐스트 범위 결정
                MessagePublisherType publishType = map(req.chatAreaType());

                return packetSender.broadcast(context.sessionId(), publishType, packet);
            })
            .onErrorResume(e -> {
                log.error("Invalid chat payload: {}", e.getMessage(), e);
                return Mono.empty();
            });
    }

    private MessagePublisherType map(MessagePublisherType chatAreaType) {
        return switch (chatAreaType) {
            case PRIVATE_AREA -> MessagePublisherType.PRIVATE_AREA;
            case PUBLIC_AREA  -> MessagePublisherType.PUBLIC_AREA;
        };
    }
}
