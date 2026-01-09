package site.codecrew.world.master.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record WorldMember(WorldCode world, IdentityHash identityHash) {

    public WorldMember {
        if (world == null) {
            throw new IllegalArgumentException("WorldId required");
        }
        if (identityHash == null) {
            throw new IllegalArgumentException("PlayerId required");
        }
    }

    public static WorldMember of(String rawWorldCode, String issuer, String subject) {
        return new WorldMember(new WorldCode(rawWorldCode), IdentityHash.from(issuer, subject));
    }
}