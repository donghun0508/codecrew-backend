package site.codecrew.world.domain.routing;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SessionRoutingRegistry {

    // 트리 구성
    Mono<Void> addEdge(NodeId parent, NodeId child);

    // 멤버십 (WORLD 금지, 세션은 항상 한 곳에만)
    Mono<Void> attach(NodeId node, Session session);
    Mono<Void> detach(Session session);

    Mono<NodeId> findCurrentNode(String sessionId);
    Mono<Session> findSession(String sessionId);

    // sessionId 기준 브로드캐스트 대상 세션들
    Flux<Session> resolveTargets(String sessionId, MessagePublisherType type);

    // nodeId 기준 브로드캐스트 대상 세션들
    Flux<Session> resolveTargets(NodeId node, MessagePublisherType type);
}
