package site.codecrew.world.domain.player;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;
import site.codecrew.r2dbc.jpa.AggregateRoot;
import site.codecrew.world.domain.connection.MapNodeId;
import site.codecrew.world.domain.connection.NodeId;
import site.codecrew.world.domain.connection.RoomNodeId;

@Getter
@Table("player")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Player extends AggregateRoot {

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

    public void updateCoordinate(int x, int y) {
        this.coordinate = new Coordinate(x, y);
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


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return Objects.equals(getIdentityHash(), player.getIdentityHash());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getIdentityHash());
    }

    @Override
    public String toString() {
        return "Player{" +
            "identityHash='" + identityHash + '\'' +
            ", id=" + id +
            '}';
    }
}