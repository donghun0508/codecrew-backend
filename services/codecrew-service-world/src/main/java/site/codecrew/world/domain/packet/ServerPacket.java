package site.codecrew.world.domain.packet;

import com.fasterxml.jackson.annotation.JsonInclude;
import site.codecrew.world.domain.packet.payload.Payload;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ServerPacket<T extends Payload>(
    PacketHeader header,
    T payload
) {
    public static <T extends Payload> ServerPacket<T> success(PacketType type, T payload) {
        return new ServerPacket<>(
            PacketHeader.of(type, null),
            payload
        );
    }

    public static <T extends Payload> ServerPacket<T> from(PacketType type, String senderId, T payload) {
        return new ServerPacket<>(
            PacketHeader.of(type, senderId),
            payload
        );
    }
}