package site.codecrew.world.application.frame;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketMessage.Type;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.packet.Header;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.Payload;
import site.codecrew.world.domain.packet.client.InboundPacketHandler;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
class PlainTextFrameHandler implements WorldSocketFrameHandler {

    private final ObjectMapper objectMapper;
    private final Map<PacketType, StrategyRegistration> strategyMap;

    private record RawPacket(Header header, JsonNode payload) {}
    private record StrategyRegistration(InboundPacketHandler<?> handler, Class<?> payloadClass) {}

    public PlainTextFrameHandler(ObjectMapper objectMapper, List<InboundPacketHandler<?>> handlers) {
        this.objectMapper = objectMapper;
        this.strategyMap = new EnumMap<>(PacketType.class);

        for (InboundPacketHandler<?> handler : handlers) {
            PacketType type = handler.packetType();
            Class<?> payloadType = GenericTypeResolver.resolveTypeArgument(handler.getClass(), InboundPacketHandler.class);

            if (payloadType == null) throw new IllegalStateException("Generic type missing: " + handler.getClass());
            if (strategyMap.containsKey(type)) throw new IllegalStateException("Duplicate handler: " + type);

            strategyMap.put(type, new StrategyRegistration(handler, payloadType));
        }
    }

    @Override
    public Type getSupportedType() {
        return Type.TEXT;
    }

    @Override
    public Mono<Void> handle(Connection connection, WebSocketMessage message) {
        return Mono.fromCallable(() -> objectMapper.readValue(message.getPayloadAsText(), RawPacket.class))
            .flatMap(rawPacket -> {
                if (rawPacket.header() == null || rawPacket.header().type() == null) {
                    return Mono.error(new IllegalArgumentException("Invalid packet header"));
                }

                StrategyRegistration reg = strategyMap.get(rawPacket.header().type());
                if (reg == null) {
                    log.warn("[Packet] No handler for type: {}", rawPacket.header().type());
                    return Mono.empty();
                }

                return execute(connection, rawPacket.payload(), reg);
            })
            .onErrorResume(e -> {
                log.error("[Packet] Handle error: {}", e.getMessage(), e);
                return Mono.empty();
            });
    }

    @SuppressWarnings("unchecked")
    private <T extends Payload> Mono<Void> execute(Connection connection, JsonNode payloadNode, StrategyRegistration reg) {
        if (payloadNode == null) {
            return Mono.error(new IllegalArgumentException("Payload is missing"));
        }

        Class<T> targetClass = (Class<T>) reg.payloadClass();
        T payload = objectMapper.convertValue(payloadNode, targetClass);

        InboundPacketHandler<T> handler = (InboundPacketHandler<T>) reg.handler();
        return handler.handle(connection, payload);
    }
}