package site.codecrew.world.domain.routing;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class RoutingService {

    private final SessionRoutingRegistry registry;

    public Mono<Void> register(SessionRoute sessionRoute) {
        return registry.attach(sessionRoute.nodeId(), sessionRoute.session());
    }

    public Mono<Void> unregister(Session session) {
        return registry.detach(session);
    }

    public Flux<Session> resolveTargets(String publisherSessionId, MessagePublisherType type) {
        return registry.resolveTargets(publisherSessionId, type);
    }

    public Mono<Session> findSession(String sessionId) {
        return registry.findSession(sessionId);
    }

    public Mono<Void> moveToRoom(long worldId, long mapId, long roomId, Session session) {
        return registry.attach(new RoomNodeId(worldId, mapId, roomId), session);
    }

    public Mono<Void> moveToMap(long worldId, long mapId, Session session) {
        return registry.attach(new MapNodeId(worldId, mapId), session);
    }
}
