package site.codecrew.world.master.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorldRepository extends JpaRepository<World, Long> {

    Optional<World> findByCode(WorldCode code);
}
