package site.codecrew.world.domain.player;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MissionRepository extends R2dbcRepository<Mission, Long> {

    @Query("SELECT * FROM mission WHERE player_id = :playerId AND DATE(created_at) = :date")
    Flux<Mission> findAllByPlayerIdAndDate(long playerId, LocalDate date);

    Flux<Long> findByPlayerIdAndMissionStatusIn(Long playerId, Collection<MissionStatus> statuses);
    Mono<Void> deleteByPlayerIdAndMissionStatusIn(Long playerId, Collection<MissionStatus> statuses);
}
