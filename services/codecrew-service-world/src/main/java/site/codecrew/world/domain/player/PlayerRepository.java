package site.codecrew.world.temp.domain.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;
import site.codecrew.world.temp.domain.Player;

public interface PlayerRepository extends R2dbcRepository<Player, Long> {

    Mono<Player> findByIdentityHash(String identityHash);
}
