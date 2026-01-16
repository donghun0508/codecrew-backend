package site.codecrew.world.domain.packet.client.payload;

import site.codecrew.world.domain.packet.Payload;

public record MissionRegisterClientPayload(
    String title,
    String description,
    String githubUrl
) implements Payload {

}
