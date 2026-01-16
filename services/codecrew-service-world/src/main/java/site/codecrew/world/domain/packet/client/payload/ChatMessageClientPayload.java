package site.codecrew.world.domain.packet.client.payload;

import site.codecrew.world.domain.packet.MessagePublisherType;
import site.codecrew.world.domain.packet.Payload;

public record ChatMessageClientPayload(
    String message,
    MessagePublisherType type
) implements Payload {

}
