package site.codecrew.world.domain.packet.model;

import site.codecrew.world.domain.world.World;

public record WorldModel(
    String name,
    String host,
    String description,
    int maxCapacity,
    int currentCapacity
) {

    public static WorldModel from(World world) {
        return new WorldModel(
            world.getName(),
            world.getHost(),
            world.getDescription(),
            world.getMaxCapacity(),
            world.getCurrentCapacity().get());
    }
}
