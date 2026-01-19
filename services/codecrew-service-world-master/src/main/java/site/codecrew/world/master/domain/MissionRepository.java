package site.codecrew.world.master.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Modifying
    @Query("DELETE FROM Mission m WHERE m.playerId = :playerId")
    void deleteByPlayerId(@Param("playerId") Long playerId);
}
