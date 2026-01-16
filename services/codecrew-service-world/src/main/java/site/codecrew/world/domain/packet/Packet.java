package site.codecrew.world.domain.packet;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Packet<T extends Payload>(
    Header header,
    T payload
) {

    public static <T extends Payload> Packet<T> of(PacketType type, T payload) {
        return of(type, null, payload);
    }

    public static <T extends Payload> Packet<T> of(PacketType type, String senderId, T payload) {
        return new Packet<>(
            Header.of(type, senderId),
            payload
        );
    }
}