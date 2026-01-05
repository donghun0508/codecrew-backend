package site.codecrew.world.domain.repository;

import reactor.core.publisher.Mono;
import site.codecrew.world.domain.PlayerConnection;

public interface PlayerConnectionRepository extends Repository<PlayerConnection, String> {

    Mono<Boolean> existsByPlayerId(String playerId);
}