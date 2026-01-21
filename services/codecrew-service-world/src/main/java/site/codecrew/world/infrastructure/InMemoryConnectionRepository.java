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

    private final Map<String, Connection> connectionIndex = new ConcurrentHashMap<>();
    private final Map<String, Connection> playerIndex = new ConcurrentHashMap<>();
    private final Map<NodeId, Set<Connection>> channels = new ConcurrentHashMap<>();

    @Override
    public Mono<Connection> save(Connection connection) {
        return Mono.fromRunnable(() -> {
            connectionIndex.put(connection.getId(), connection);
            playerIndex.put(connection.getIdentityHash(), connection);

            channels.computeIfAbsent(connection.getNodeId(), k -> ConcurrentHashMap.newKeySet())
                .add(connection);
        }).thenReturn(connection);
    }

    @Override
    public Mono<Connection> findById(String id) {
        return Mono.justOrEmpty(connectionIndex.get(id));
    }

    @Override
    public Mono<Boolean> saveIfAbsent(Connection connection) {
        return Mono.fromCallable(() -> {
            Connection existing = connectionIndex.putIfAbsent(connection.getId(), connection);

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
        return Mono.just(connectionIndex.containsKey(id));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return Mono.fromRunnable(() -> {
            Connection connection = connectionIndex.remove(id);

            if (connection != null) {
                playerIndex.remove(connection.getIdentityHash());
                removeFromChannel(connection.getNodeId(), connection);
            }
        });
    }

    @Override
    public Mono<Void> move(String id, NodeId targetNodeId) {
        return Mono.fromRunnable(() -> {
            Connection connection = connectionIndex.get(id);
            if (connection != null) {
                removeFromChannel(connection.getNodeId(), connection);

                connection.moveTo(targetNodeId);
                channels.computeIfAbsent(targetNodeId, k -> ConcurrentHashMap.newKeySet())
                    .add(connection);
            }
        });
    }

    @Override
    public Mono<Connection> findByPlayerId(String playerId) {
        return Mono.justOrEmpty(playerIndex.get(playerId));
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
