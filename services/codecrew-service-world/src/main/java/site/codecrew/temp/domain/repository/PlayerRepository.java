package site.codecrew.world.domain.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.Player;

public interface PlayerRepository extends R2dbcRepository<Player, Long> {

    Mono<Player> findByPublicPlayerId(String playerId);
}
