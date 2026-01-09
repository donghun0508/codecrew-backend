package site.codecrew.world.domain.packet;

import reactor.core.publisher.Mono;
import site.codecrew.world.domain.packet.payload.Payload;

public interface PacketSerializer {
    <T extends Payload> Mono<String> serialize(ServerPacket<T> packet);
}
