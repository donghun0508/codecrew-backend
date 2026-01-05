package site.codecrew.world.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.adapter.filter.EnterToken;
import site.codecrew.world.domain.Player;
import site.codecrew.world.domain.PlayerService;
import site.codecrew.world.domain.WorldService;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApprovalService {

    private final PlayerService playerService;
    private final WorldService worldService;

    public Mono<Player> approveEntry(EnterToken credentials) {
        return playerService.getPlayerAndLocalSave(credentials.playerId())
            .doOnNext(player -> log.info("Approving entry for player {}", player))
            .flatMap(player ->
                worldService.reserve(credentials.worldId()).thenReturn(player)
            );
    }
}
