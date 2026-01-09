package site.codecrew.world.master.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    boolean existsByWorldIdAndIdentityHash(Long worldId, IdentityHash identityHash);
    Optional<Player> findByIdentityHash(IdentityHash identityHash);
}
