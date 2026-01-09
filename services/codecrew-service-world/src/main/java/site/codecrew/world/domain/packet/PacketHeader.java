package site.codecrew.world.domain.packet;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.UUID;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PacketHeader(
    PacketType type,
    String msgId,
    String senderId,
    long timestamp,
    String version
) {
    public static PacketHeader of(PacketType type, String senderId) {
        return PacketHeader.builder()
            .type(type)
            .msgId(UUID.randomUUID().toString()) // *이동 패킷 등 빈번한 건 ID 생략 고려
            .senderId(senderId)
            .timestamp(System.currentTimeMillis())
            .build();
    }
}