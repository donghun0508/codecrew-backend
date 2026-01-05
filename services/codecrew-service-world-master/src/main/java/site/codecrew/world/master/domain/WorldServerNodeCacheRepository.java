package site.codecrew.world.master.domain;

import java.util.Optional;

public interface WorldServerNodeCacheRepository {

    Optional<ServerNode> findByWorldId(long worldId);

    void save(long worldId, ServerNode serverNode);
}
