package site.codecrew.world.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;
import site.codecrew.world.domain.repository.PlayerRepository;

@RequiredArgsConstructor
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public Mono<Player> getPlayerAndLocalSave(String playerId) {
        return playerRepository.findByPublicPlayerId(playerId)
            .switchIfEmpty(Mono.error(new CoreException(CoreErrorCode.NOT_FOUND, "Player not found", playerId)))
            ;
    }
}
