package site.codecrew.world.domain.player;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;

@RequiredArgsConstructor
@Service
public class MissionService {

    private final MissionRepository missionRepository;

    public Flux<Mission> findAllByPlayerIdToday(long playerId) {
        return missionRepository.findAllByPlayerIdAndDate(playerId, LocalDate.now());
    }

    public Mono<Mission> fetchById(long missionId) {
        return missionRepository.findById(missionId)
            .switchIfEmpty(Mono.error(new CoreException(CoreErrorCode.NOT_FOUND, "미션을 찾을 수 없습니다.", missionId)));
    }

    public Mono<Mission> save(Mission mission) {
        return missionRepository.save(mission);
    }

    public Mono<Void> deleteById(long missionId) {
        return missionRepository.deleteById(missionId);
    }

    public Flux<Long> findByPlayerIdAndMissionStatusIn(Long playerId, Collection<MissionStatus> statuses) {
        return missionRepository.findByPlayerIdAndMissionStatusIn(playerId, statuses);
    }

    public Mono<Void> deleteAllByIds(List<Long> missionIds) {
        return missionRepository.deleteAllById(missionIds);
    }
}
