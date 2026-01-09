package site.codecrew.world.domain.packet.payload.client;

import site.codecrew.world.domain.routing.MessagePublisherType;

public record ChatSendPayload(
    String message,
    MessagePublisherType chatAreaType
) {

}
