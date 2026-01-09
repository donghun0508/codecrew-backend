package site.codecrew.world.infrastructure;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.routing.MessagePublisherType;
import site.codecrew.world.domain.routing.NodeId;
import site.codecrew.world.domain.routing.NodeType;
import site.codecrew.world.domain.routing.Session;
import site.codecrew.world.domain.routing.SessionRoutingRegistry;

@RequiredArgsConstructor
@Component
class InMemorySessionRoutingRegistry implements SessionRoutingRegistry {

    // ===== 트리 구조 =====
    private final ConcurrentHashMap<NodeId, Set<NodeId>> childrenByNode = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<NodeId, NodeId> parentByNode = new ConcurrentHashMap<>();

    // ===== 멤버십 (WORLD에는 절대 안 붙음) =====
    private final ConcurrentHashMap<NodeId, Set<Session>> sessionsByNode = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, NodeId> currentNodeBySessionId = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> addEdge(NodeId parent, NodeId child) {
        return Mono.fromRunnable(() -> {
            boolean allowed =
                (parent.type() == NodeType.WORLD && child.type() == NodeType.MAP) ||
                    (parent.type() == NodeType.MAP && child.type() == NodeType.ROOM);

            if (!allowed) {
                throw new IllegalArgumentException("invalid edge: " + parent + " -> " + child);
            }

            parentByNode.put(child, parent);
            childrenByNode.computeIfAbsent(parent, k -> ConcurrentHashMap.newKeySet()).add(child);
        });
    }

    @Override
    public Mono<Void> attach(NodeId node, Session session) {
        if (node.type() == NodeType.WORLD) {
            return Mono.error(new IllegalArgumentException("WORLD node cannot have sessions"));
        }
        return Mono.fromRunnable(() -> {
            // 이전 소속 제거
            NodeId prev = currentNodeBySessionId.put(session.id(), node);
            if (prev != null) {
                removeFromNode(prev, session);
            }
            sessionsByNode.computeIfAbsent(node, k -> ConcurrentHashMap.newKeySet()).add(session);
        });
    }

    @Override
    public Mono<Void> detach(Session session) {
        return Mono.fromRunnable(() -> {
            NodeId prev = currentNodeBySessionId.remove(session.id());
            if (prev != null) {
                removeFromNode(prev, session);
            }
        });
    }

    @Override
    public Mono<NodeId> findCurrentNode(String sessionId) {
        return Mono.justOrEmpty(currentNodeBySessionId.get(sessionId));
    }

    @Override
    public Mono<Session> findSession(String sessionId) {
        return findCurrentNode(sessionId)
            .flatMap(node -> Mono.fromSupplier(() -> sessionsByNode.getOrDefault(node, Set.of())))
            .flatMapMany(Flux::fromIterable)
            .filter(s -> s.id().equals(sessionId))
            .next();
    }

    @Override
    public Flux<Session> resolveTargets(String sessionId, MessagePublisherType type) {
        return findCurrentNode(sessionId)
            .switchIfEmpty(Mono.error(new IllegalStateException("session not attached: " + sessionId)))
            .flatMapMany(node -> resolveTargets(node, type));
    }

    @Override
    public Flux<Session> resolveTargets(NodeId node, MessagePublisherType type) {
        return switch (type) {
            case PRIVATE_AREA -> resolvePrivateTargets(node);
            case PUBLIC_AREA -> resolvePublicTargets(node);
        };
    }

    // PRIVATE: 현재 ROOM이면 해당 ROOM, 아니면 현재 MAP(또는 ROOM 아닌 값은 없음)
    private Flux<Session> resolvePrivateTargets(NodeId node) {
        if (node.type() == NodeType.ROOM) {
            return membersOf(node);
        }
        if (node.type() == NodeType.MAP) {
            return membersOf(node);
        }
        return Flux.empty();
    }

    // PUBLIC: MAP 범위 (MAP + 하위 ROOM 전부). 현재가 ROOM이면 부모 MAP으로 확장.
    private Flux<Session> resolvePublicTargets(NodeId node) {
        NodeId mapNode = switch (node.type()) {
            case MAP -> node;
            case ROOM -> parentByNode.get(node); // room -> map
            case WORLD -> null;
        };
        if (mapNode == null || mapNode.type() != NodeType.MAP) {
            return Flux.empty();
        }
        return Flux.concat(
            membersOf(mapNode),
            membersOfDirectRooms(mapNode)
        );
    }

    private Flux<Session> membersOf(NodeId node) {
        return Mono.fromSupplier(() -> sessionsByNode.getOrDefault(node, Set.of()))
            .flatMapMany(Flux::fromIterable);
    }

    private Flux<Session> membersOfDirectRooms(NodeId mapNode) {
        return Flux.fromIterable(childrenByNode.getOrDefault(mapNode, Set.of()))
            .filter(n -> n.type() == NodeType.ROOM)
            .flatMap(this::membersOf);
    }

    private void removeFromNode(NodeId node, Session session) {
        sessionsByNode.computeIfPresent(node, (k, set) -> {
            set.remove(session);
            return set.isEmpty() ? null : set;
        });
    }
}