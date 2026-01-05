package site.codecrew.world.domain;

import org.springframework.data.relational.core.mapping.Column;

public record Location(
    @Column("last_map_id")
    long mapId,

    @Column("last_room_id")
    long roomId,

    @Column("last_posX")
    double x,

    @Column("last_posY")
    double y
) {
}