package site.codecrew.world.master.domain;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WorldServerNodeService {

    private final WorldServerNodeCacheRepository repository;

    public Optional<ServerNode> findByWorldId(long worldId) {
        return repository.findByWorldId(worldId);
    }

    public void save(long worldId, ServerNode newServerNode) {
        repository.save(worldId, newServerNode);
    }
}
