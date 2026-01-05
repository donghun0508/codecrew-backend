package site.codecrew.world.master.domain;

public interface PlayerCacheRepository {

    boolean existsByWorldIdAndPlayerId(Long worldId, PlayerId playerId);

    void save(Player savedPlayer);
}
