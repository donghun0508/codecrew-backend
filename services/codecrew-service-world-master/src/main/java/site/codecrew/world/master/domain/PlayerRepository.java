package site.codecrew.world.master.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    boolean existsByWorldIdAndPlayerId(Long worldId, PlayerId playerId);
    Optional<Player> findByPlayerId(PlayerId playerId);
}
