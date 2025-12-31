package site.codecrew.world.master.domain;

import java.util.Optional;

public interface WorldServerNodeCacheRepository {

    Optional<ServerNode> findByWorldCode(WorldCode worldCode);

    void save(WorldCode worldCode, ServerNode serverNode);
}
