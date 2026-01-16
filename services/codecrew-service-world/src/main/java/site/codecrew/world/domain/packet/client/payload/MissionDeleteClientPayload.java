package site.codecrew.world.domain.packet.client.payload;

import site.codecrew.world.domain.packet.Payload;

public record MissionDeleteClientPayload(
    long missionId
) implements Payload {

}
