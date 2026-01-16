package site.codecrew.world.domain.connection;


public record RoomNodeId(long worldId, long mapId, long roomId) implements NodeId {
    @Override public NodeType type() { return NodeType.ROOM; }

    @Override
    public boolean belongsTo(NodeId parent) {
        if (parent instanceof WorldNodeId(long worldId1)) {
            return this.worldId == worldId1;
        }
        if (parent instanceof MapNodeId(long id, long mapId1)) {
            return this.worldId == id && this.mapId == mapId1;
        }
        if (parent instanceof RoomNodeId(long id, long mapId1, long roomId1)) {
            return this.worldId == id && this.mapId == mapId1 && this.roomId == roomId1;
        }
        return false;
    }
}