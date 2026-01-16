package site.codecrew.world.infrastructure;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.connection.ConnectionRepository;
import site.codecrew.world.domain.connection.NodeId;

@Repository
class InMemoryConnectionRepository implements ConnectionRepository {

    private final Map<String, Connection> store = new ConcurrentHashMap<>();
    private final Map<NodeId, Set<Connection>> channels = new ConcurrentHashMap<>();

    @Override
    public Mono<Connection> save(Connection connection) {
        return Mono.fromRunnable(() -> {
            store.put(connection.getId(), connection);

            channels.computeIfAbsent(connection.getNodeId(), k -> ConcurrentHashMap.newKeySet())
                .add(connection);
        }).thenReturn(connection);
    }

    @Override
    public Mono<Connection> findById(String id) {
        return Mono.justOrEmpty(store.get(id));
    }

    @Override
    public Mono<Boolean> saveIfAbsent(Connection connection) {
        return Mono.fromCallable(() -> {
            Connection existing = store.putIfAbsent(connection.getId(), connection);

            if (existing == null) {
                channels.computeIfAbsent(connection.getNodeId(), k -> ConcurrentHashMap.newKeySet())
                    .add(connection);
                return true;
            }
            return false;
        });
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return Mono.just(store.containsKey(id));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return Mono.fromRunnable(() -> {
            Connection connection = store.remove(id);

            if (connection != null) {
                removeFromChannel(connection.getNodeId(), connection);
            }
        });
    }

    @Override
    public Mono<Void> move(String id, NodeId targetNodeId) {
        return Mono.fromRunnable(() -> {
            Connection connection = store.get(id);
            if (connection != null) {
                removeFromChannel(connection.getNodeId(), connection);

                connection.moveTo(targetNodeId);
                channels.computeIfAbsent(targetNodeId, k -> ConcurrentHashMap.newKeySet())
                    .add(connection);
            }
        });
    }


    @Override
    public Flux<Connection> findAllByNode(NodeId nodeId) {
        return Flux.fromIterable(channels.entrySet())
            .filter(entry -> entry.getKey().belongsTo(nodeId))
            .flatMapIterable(Map.Entry::getValue);
    }

    private void removeFromChannel(NodeId nodeId, Connection connection) {
        Set<Connection> channel = channels.get(nodeId);
        if (channel != null) {
            channel.remove(connection);
            if (channel.isEmpty()) {
                channels.remove(nodeId);
            }
        }
    }
}
