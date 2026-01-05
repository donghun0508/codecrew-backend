package site.codecrew.world.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.SessionRouting;
import site.codecrew.world.domain.SessionRoutingService;

@RequiredArgsConstructor
@Component
public class RoutingService {

    private final SessionRoutingService sessionRoutingService;

    public Mono<Void> routeSession(Connection connection) {
        SessionRouting sessionRouting = new SessionRouting(
            connection.worldSession().sessionId(),
            connection.player().getLocation().mapId()
        );
        return sessionRoutingService.saveSessionRouting(sessionRouting);
    }
}
