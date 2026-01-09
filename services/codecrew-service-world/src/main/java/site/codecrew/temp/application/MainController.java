package site.codecrew.world.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class MainController {

    private final ApprovalService approvalService;
    private final ConnectionRegisterService registerService;
    private final RoutingService routingService;
    private final InitialSyncService initialSyncService;
    private final ConnectionMonitoringService monitoringService;
    private final MessageListener messageListener;
    private final ConnectionCleaner cleaner;

    public Mono<Void> enter(WorldSession worldSession) {
        return approvalService.approveEntry(worldSession.credentials())
            .map(player -> Connection.from(player, worldSession))
            .flatMap(registerService::register)
            .flatMap(routingService::routeSession)
            .then(initialSyncService.performInitialSync(worldSession))
            .then(messageListener.startListening(worldSession))
            .then()
            .doFinally(signalType -> cleaner.cleanup(worldSession).subscribe());
    }
}