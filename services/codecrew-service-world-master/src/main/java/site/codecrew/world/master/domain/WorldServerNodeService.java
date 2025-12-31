package site.codecrew.world.master.domain;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WorldServerNodeService {

    private final WorldServerNodeCacheRepository repository;

    public Optional<ServerNode> findByWorldCode(WorldCode worldCode) {
        return repository.findByWorldCode(worldCode);
    }

    public void save(WorldCode worldCode, ServerNode newServerNode) {
        repository.save(worldCode, newServerNode);
    }
}
