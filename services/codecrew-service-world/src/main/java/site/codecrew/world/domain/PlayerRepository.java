package site.codecrew.world.domain;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface PlayerRepository extends R2dbcRepository<Player, Long> {

    Mono<Player> findByIdentityHash(String identityHash);
}
