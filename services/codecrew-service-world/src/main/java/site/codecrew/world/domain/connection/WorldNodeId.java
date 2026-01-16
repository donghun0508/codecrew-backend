package site.codecrew.world.domain.connection;


public record WorldNodeId(long worldId) implements NodeId {

    public static WorldNodeId from(long worldId) {
        return new WorldNodeId(worldId);
    }

    @Override public NodeType type() { return NodeType.WORLD; }

    @Override
    public boolean belongsTo(NodeId parent) {
        if (parent instanceof WorldNodeId(long id)) {
            return this.worldId == id;
        }
        return false;
    }
}