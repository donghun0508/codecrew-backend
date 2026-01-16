package site.codecrew.world.domain.network;

import site.codecrew.world.temp.domain.routing.NodeType;

public record WorldNodeId(long worldId) implements NodeId {

    public static WorldNodeId from(long worldId) {
        return new WorldNodeId(worldId);
    }

    @Override public NodeType type() { return NodeType.WORLD; }
}