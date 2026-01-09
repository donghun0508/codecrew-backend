package site.codecrew.world.domain.packet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.packet.payload.Payload;
import tools.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
@Component
class JacksonPacketSerializer implements PacketSerializer {

    private final ObjectMapper objectMapper;

    @Override
    public <T extends Payload> Mono<String> serialize(ServerPacket<T> packet) {
        return Mono.fromCallable(() -> objectMapper.writeValueAsString(packet));
    }
}