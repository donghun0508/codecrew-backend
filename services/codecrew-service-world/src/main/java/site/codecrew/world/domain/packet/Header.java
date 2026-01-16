package site.codecrew.world.domain.packet;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.UUID;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Header(
    PacketType type,
    String msgId,
    String sessionId,
    Long timestamp,
    String version
) {

    public static Header of(PacketType type, String sessionId) {
        return Header.builder()
            .type(type)
            .msgId(UUID.randomUUID().toString())
            .sessionId(sessionId)
            .timestamp(System.currentTimeMillis())
            .build();
    }

    public static Header of(PacketType type) {
        return Header.builder()
            .type(type)
            .msgId(UUID.randomUUID().toString())
            .sessionId(null)
            .timestamp(System.currentTimeMillis())
            .build();
    }
}