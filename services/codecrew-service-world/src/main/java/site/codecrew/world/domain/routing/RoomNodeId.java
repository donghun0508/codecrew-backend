package site.codecrew.world.domain.routing;

public record RoomNodeId(long worldId, long mapId, long roomId) implements NodeId {
    @Override public NodeType type() { return NodeType.ROOM; }
}