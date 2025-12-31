package site.codecrew.world.master.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    boolean existsByWorldIdAndPlayerId(Long worldId, PlayerId playerId);
}


// boolean existsByWorldIdAndPlayerId(WorldId world, MemberId memberId);
