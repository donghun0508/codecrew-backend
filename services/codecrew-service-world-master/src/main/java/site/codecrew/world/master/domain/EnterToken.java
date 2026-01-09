package site.codecrew.world.master.domain;

import java.util.UUID;

public record EnterToken(String rawToken, Long worldId, IdentityHash identityHash) {

    public static EnterToken generate(Long worldId, IdentityHash identityHash) {
        String rawEnterToken = UUID.randomUUID().toString().replace("-", "");
        return new EnterToken(rawEnterToken, worldId, identityHash);
    }

}
