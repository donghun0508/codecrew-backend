package site.codecrew.world.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.routing.RoutingService;

@Slf4j
@RequiredArgsConstructor
@Component
public class WorldSessionManager {

    private final WorldService worldService;
    private final PlayerService playerService;
    private final RoutingService routingService;

    public Mono<Void> initializeSession(WorldSessionContext context) {
        return playerService.checkDuplicateLogin(context.identityHash())
            .then(Mono.zip(
                worldService.fetchById(context.worldId()),
                playerService.fetchByIdentityHash(context.identityHash())
            ))
            .doOnNext(context::bind)
            .doOnSuccess(v -> context.markMetadataLoaded())
            .then(Mono.fromRunnable(() -> log.debug("[Session] Initialized & Loaded: {}", context)));
    }

    public Mono<Void> register(WorldSessionContext context) {
        return worldService.reserve(context.world())
            .doOnSuccess(v -> context.markSlotOccupied())
            .then(Mono.fromRunnable(() -> log.debug("[Session] World Reserved: {}", context)))
            .then(playerService.register(context.player()))
            .doOnSuccess(v -> context.markDbSaved())
            .then(Mono.fromRunnable(() -> log.debug("[Session] Player Saved: {}", context)))
            .then(routingService.register(context.sessionRoute()))
            .doOnSuccess(v -> context.markRoutingSaved())
            .then(Mono.fromRunnable(() -> log.debug("[Session] Routing Registered & Complete: {}", context)));
    }

    public Mono<Void> release(WorldSessionContext context) {
        return safely(routingService.unregister(context.session()))
            .then(safely(playerService.unregister(context.player())))
            .then(safely(worldService.release(context.world())))
            .doOnSuccess(v -> log.debug("[Session] Released: {}", context));
    }

    private Mono<Void> safely(Mono<Void> publisher) {
        return publisher
            .doOnError(e -> log.error("[Session] Resource release failed", e))
            .onErrorResume(e -> Mono.empty());
    }

}