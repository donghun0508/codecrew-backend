package site.codecrew.world.master.domain;

import java.util.UUID;

public record EnterToken(String rawToken, Long worldId, PlayerId playerId) {

    public static EnterToken generate(Long worldId, PlayerId playerId) {
        String rawEnterToken = UUID.randomUUID().toString().replace("-", "");
        return new EnterToken(rawEnterToken, worldId, playerId);
    }

}
