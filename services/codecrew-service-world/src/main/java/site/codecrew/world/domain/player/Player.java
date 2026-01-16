package site.codecrew.world.temp.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;
import site.codecrew.world.temp.domain.routing.MapNodeId;
import site.codecrew.world.temp.domain.routing.NodeId;
import site.codecrew.world.temp.domain.routing.RoomNodeId;

@Getter
@Table("player")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Player {

    @Id
    @Column("player_id")
    private Long id;

    @Column("identity_hash")
    private String identityHash;

    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private Avatar avatar;

    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private Location location;

    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    Coordinate coordinate;

    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private PlayerAttribute attribute;

    public void assertEnterable() {

    }

    public Long id() {
        return id;
    }

    public NodeId nodeId() {
        if (location == null) {
            throw new IllegalStateException("location is null");
        }

        long worldId = location.worldId();
        long mapId = location.mapId();
        long roomId = location.roomId();

        if (roomId <= 0) {
            return new MapNodeId(worldId, mapId);
        }
        return new RoomNodeId(worldId, mapId, roomId);
    }

}