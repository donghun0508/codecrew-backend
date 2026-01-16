package site.codecrew.world.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.connection.ConnectionService;
import site.codecrew.world.domain.player.PlayerService;
import site.codecrew.world.domain.world.WorldService;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorldSessionFacade {

    private final PlayerService playerService;
    private final WorldService worldService;
    private final ConnectionService connectionService;

    public Mono<Connection> enter(WorldEntryCommand command) {
        return worldService.ensureCapacityAvailable(command.worldId())
            .then(playerService.fetchEnterable(command.identityHash()))
            .delayUntil(player -> worldService.tryOccupy(command.worldId()))
            .flatMap(player -> connectionService.register(command.session(), player));
    }

    public Mono<Void> leave(Connection connection) {
        return safely(worldService.vacate(connection.worldId()))
            .then(safely(playerService.deleteById(connection.getIdentityHash())))
            .then(safely(connectionService.delete(connection)));
    }

    private Mono<Void> safely(Mono<Void> publisher) {
        return publisher
            .doOnError(e -> log.warn("[Leave] Cleanup failed: {}", e.getMessage()))
            .onErrorResume(e -> Mono.empty());
    }
}
