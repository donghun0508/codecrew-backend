package site.codecrew.world.master.domain;

public interface AvatarCacheRepository {

    boolean existsByWorldIdAndPlayerId(Long worldId, PlayerId playerId);

    void save(Avatar savedAvatar);
}
