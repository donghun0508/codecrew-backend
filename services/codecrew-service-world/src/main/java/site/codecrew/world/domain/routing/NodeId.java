package site.codecrew.world.domain.routing;

public sealed interface NodeId permits WorldNodeId, MapNodeId, RoomNodeId {
    NodeType type();
}