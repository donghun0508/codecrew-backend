package site.codecrew.world.domain.connection;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.LocalRepository;

public interface ConnectionRepository extends LocalRepository<Connection, String>{

    Mono<Void> move(String connectionId, NodeId targetNodeId);

    Flux<Connection> findAllByNode(NodeId nodeId);
}
