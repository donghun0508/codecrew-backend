package site.codecrew.world.master.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MissionService {

    private final MissionRepository missionRepository;

    public void deleteByPlayerId(Long playerId) {
        missionRepository.deleteByPlayerId(playerId);
    }
}
