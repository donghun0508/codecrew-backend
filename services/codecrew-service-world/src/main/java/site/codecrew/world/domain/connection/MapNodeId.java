package site.codecrew.world.domain.network;

import site.codecrew.world.temp.domain.routing.NodeType;

public record MapNodeId(long worldId, long mapId) implements NodeId {
    @Override public NodeType type() { return NodeType.MAP; }
}