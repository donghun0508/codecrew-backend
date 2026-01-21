package site.codecrew.world.domain.packet.client.payload;

import site.codecrew.world.domain.packet.Payload;

public record NudgeRequestPayload(
    String targetPlayerId
) implements Payload {

}
