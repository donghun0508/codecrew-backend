package site.codecrew.world.master.domain;

import java.util.UUID;

public record EnterToken(String rawToken, WorldMember worldMember) {

    public static EnterToken generate(WorldMember worldMember) {
        String rawEnterToken = UUID.randomUUID().toString().replace("-", "");
        return new EnterToken(rawEnterToken, worldMember);
    }
}
