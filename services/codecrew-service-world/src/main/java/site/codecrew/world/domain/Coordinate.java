package site.codecrew.world.domain;

import org.springframework.data.relational.core.mapping.Column;

public record Coordinate(
    @Column("last_posX") double x,
    @Column("last_posY") double y
) {}