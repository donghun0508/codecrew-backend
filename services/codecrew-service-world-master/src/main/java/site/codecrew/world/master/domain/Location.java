package site.codecrew.world.master.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record Location(
    @Column(name = "last_map_id", length = 50)
    long mapId,
    @Column(name = "last_room_id", length = 50)
    long roomId,
    @Column(name = "last_posX")
    double x,
    @Column(name = "last_posY")
    double y
) {

    public static Location initialize() {
        return new Location(1L, 1L, 0.0, 0.0);
    }
}
