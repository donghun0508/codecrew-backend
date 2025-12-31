package site.codecrew.world.master.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WorldEnterTokenService {

    private final WorldEnterTokenCacheRepository worldEnterTokenCacheRepository;

    public void save(EnterToken enterToken) {
        worldEnterTokenCacheRepository.save(enterToken);
    }
}
