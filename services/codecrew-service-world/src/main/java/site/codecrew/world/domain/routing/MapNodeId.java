package site.codecrew.world.domain.routing;

public record MapNodeId(long worldId, long mapId) implements NodeId {
    @Override public NodeType type() { return NodeType.MAP; }
}