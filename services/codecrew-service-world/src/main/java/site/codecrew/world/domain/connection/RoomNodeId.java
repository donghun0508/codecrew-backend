package site.codecrew.world.domain.network;

import site.codecrew.world.temp.domain.routing.NodeType;

public record RoomNodeId(long worldId, long mapId, long roomId) implements NodeId {
    @Override public NodeType type() { return NodeType.ROOM; }
}