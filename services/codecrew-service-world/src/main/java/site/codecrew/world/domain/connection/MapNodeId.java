package site.codecrew.world.domain.connection;


public record MapNodeId(long worldId, long mapId) implements NodeId {
    @Override
    public NodeType type() { return NodeType.MAP; }

    @Override
    public boolean belongsTo(NodeId parent) {
        if (parent instanceof WorldNodeId(long id)) {
            return this.worldId == id;
        }
        if (parent instanceof MapNodeId(long id, long mapId1)) {
            return this.worldId == id && this.mapId == mapId1;
        }
        return false;
    }
}