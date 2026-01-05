package site.codecrew.world.domain.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ServerMessage<T>(
    String type,               // JSON 변환 시 "CHAT" 같은 문자열로 나감
    String id,
    String senderSessionId,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime timestamp,
    T payload
) {

    public static <T> ServerMessage<T> of(MessageType type, T payload) {
        return of(type, null, payload);
    }

    public static <T> ServerMessage<T> of(MessageType type, String senderSessionId, T payload) {
        return new ServerMessage<>(
            type.name(), // Enum -> String 변환 ("CHAT")
            UUID.randomUUID().toString(),
            senderSessionId,
            LocalDateTime.now(),
            payload
        );
    }
}