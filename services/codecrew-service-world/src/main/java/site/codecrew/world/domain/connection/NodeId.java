package site.codecrew.world.domain.network;

import site.codecrew.world.temp.domain.routing.NodeType;

public sealed interface NodeId permits WorldNodeId, MapNodeId, RoomNodeId {
    NodeType type();
}