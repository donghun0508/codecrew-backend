package site.codecrew.world.domain.player;

import org.springframework.data.relational.core.mapping.Column;

public record Location(
    @Column("world_id") long worldId,
    @Column("last_map_id") long mapId,
    @Column("last_room_id") Long roomId
) {}