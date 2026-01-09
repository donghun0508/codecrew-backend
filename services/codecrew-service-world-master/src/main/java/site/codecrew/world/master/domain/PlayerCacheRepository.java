package site.codecrew.world.master.domain;

public interface PlayerCacheRepository {

    boolean existsByWorldIdAndPlayerId(Long worldId, IdentityHash identityHash);

    void save(Player savedPlayer);
}
