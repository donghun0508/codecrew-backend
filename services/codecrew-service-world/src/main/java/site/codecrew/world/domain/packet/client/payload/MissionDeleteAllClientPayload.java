package site.codecrew.world.domain.packet.client.payload;

import site.codecrew.world.domain.packet.Payload;

public record MissionDeleteAllClientPayload(
    String playerId
) implements Payload {

}
