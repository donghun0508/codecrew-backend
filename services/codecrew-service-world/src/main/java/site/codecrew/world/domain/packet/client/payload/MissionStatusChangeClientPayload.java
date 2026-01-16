package site.codecrew.world.domain.packet.client.payload;

import site.codecrew.world.domain.packet.Payload;
import site.codecrew.world.domain.player.MissionStatus;

public record MissionStatusChangeClientPayload(
    long missionId,
    MissionStatus status
) implements Payload {

}
