package site.codecrew.world.domain.connection;


import lombok.Getter;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import site.codecrew.r2dbc.jpa.AggregateRoot;
import site.codecrew.world.domain.connection.ConnectionEvent.ConnectionCreatedEvent;
import site.codecrew.world.domain.player.Player;
import site.codecrew.world.domain.player.Location;

@Getter
public class Connection extends AggregateRoot {
    private String id;
    private WebSocketSession socket;
    private WorldNodeId worldNodeId;
    private long playerId;
    private String identityHash;
    private volatile NodeId nodeId;
    private Heartbeat heartbeat = new Heartbeat();

    private Connection() {
    }

    private Connection(WebSocketSession socket, Player player) {
        this.id = socket.getId();
        this.socket = socket;
        this.playerId = player.id();
        this.worldNodeId = new WorldNodeId(player.getLocation().worldId());
        this.identityHash = player.getIdentityHash();
        this.nodeId = createNodeId(player.getLocation());
    }

    public static Connection create(WebSocketSession socket, Player player) {
        Connection connection = new Connection();

        connection.id = socket.getId();
        connection.socket = socket;
        connection.worldNodeId = new WorldNodeId(player.getLocation().worldId());
        connection.identityHash = player.getIdentityHash();
        connection.nodeId = createNodeId(player.getLocation());
        connection.playerId = player.getId();
        connection.registerEvent(new ConnectionCreatedEvent(connection));

        return connection;
    }

    private static NodeId createNodeId(Location location) {
        long worldId = location.worldId();
        long mapId = location.mapId();
        long roomId = location.roomId();

        // 방 번호가 없으면(0 이하) 맵 노드, 있으면 룸 노드
        if (roomId <= 0) {
            return new MapNodeId(worldId, mapId);
        }
        return new RoomNodeId(worldId, mapId, roomId);
    }

    public void moveTo(NodeId newNodeId) {
        this.nodeId = newNodeId;
    }

    public long worldId() {
        return worldNodeId.worldId();
    }

    public void updateHeartbeat() {
        this.heartbeat.updateHeartbeat();
    }

    public long getLastHeartbeat() {
        return this.heartbeat.getLastHeartbeat();
    }

    public Mono<Void> send(String message) {
        return socket.send(Mono.just(socket.textMessage(message)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Connection that)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Connection{" +
            "id='" + id + '\'' +
            ", identityHash='" + identityHash + '\'' +
            '}';
    }
}
