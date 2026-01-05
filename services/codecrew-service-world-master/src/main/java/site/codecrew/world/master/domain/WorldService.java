package site.codecrew.world.master.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.codecrew.core.exception.CoreException;
import site.codecrew.world.master.application.exception.WorldMasterErrorCode;

@RequiredArgsConstructor
@Service
public class WorldService {

    private final WorldRepository worldRepository;

    @Transactional(readOnly = true)
    public World getByWorldCode(WorldCode code) {
        return fetchWorld(code);
    }

    private World fetchWorld(WorldCode code) {
        return worldRepository.findByCode(code)
            .orElseThrow(() -> new CoreException(WorldMasterErrorCode.WORLD_NOT_FOUND, "World not found", code));
    }
}
