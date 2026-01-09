package site.codecrew.world.master.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.codecrew.jpa.AggregateRoot;
import site.codecrew.world.master.domain.spec.CreatePlayerSpec;

@Entity
@Table(name = "player", uniqueConstraints = {
    @UniqueConstraint(name = "uk_player_world_player", columnNames = {"world_id", "player_id"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Player extends AggregateRoot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Long id;

    @Column(name = "world_id", nullable = false, updatable = false)
    private long worldId;

    @Embedded
    private IdentityHash identityHash;

    @Embedded
    private Avatar avatar;

    @Embedded
    private Location location;

    @Embedded
    private PlayerAttribute attribute;

    public static Player create(World world, CreatePlayerSpec spec) {
        spec.validate();
        world.validateAvailability();

        Player player = new Player();
        player.identityHash = spec.playerId();
        player.worldId = world.getId();
        player.avatar = spec.avatar();
        player.location = Location.initialize();
        player.attribute = spec.attribute();
        return player;
    }

    public long worldId() {
        return worldId;
    }
}