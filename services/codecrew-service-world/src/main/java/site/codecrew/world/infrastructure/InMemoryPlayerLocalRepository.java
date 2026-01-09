package site.codecrew.world.infrastructure;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import site.codecrew.world.domain.Player;
import site.codecrew.world.domain.PlayerLocalRepository;

@Repository
class InMemoryPlayerLocalRepository extends SimpleMemoryLocalRepository<Player, String> implements PlayerLocalRepository {

    protected InMemoryPlayerLocalRepository() {
        super(new ConcurrentHashMap<>(), Player::getIdentityHash);
    }
}
