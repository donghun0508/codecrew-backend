package site.codecrew.world.domain.routing;

public record WorldNodeId(long worldId) implements NodeId {
    @Override public NodeType type() { return NodeType.WORLD; }
}