package site.codecrew.world.domain.connection;


public sealed interface NodeId permits WorldNodeId, MapNodeId, RoomNodeId {
    NodeType type();
    boolean belongsTo(NodeId parent);
}