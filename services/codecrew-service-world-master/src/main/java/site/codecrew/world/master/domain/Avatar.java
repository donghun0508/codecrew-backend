package site.codecrew.world.master.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
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

@Entity
@Table(name = "avatar", uniqueConstraints = {
    @UniqueConstraint(name = "uk_avatar_world_member", columnNames = {"world_id", "player_id"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Avatar extends AggregateRoot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "world_id", nullable = false, updatable = false)
    private long worldId;

    @Embedded
    private PlayerId playerId;

    @Column(nullable = false)
    private int avatarId;

    @Column(nullable = false, length = 50)
    private String nickname;

    private Avatar(Long id, long worldId, PlayerId playerId, int avatarId, String nickname) {
        this.id = id;
        this.worldId = worldId;
        this.playerId = playerId;
        this.avatarId = avatarId;
        this.nickname = nickname;
    }

    public static Avatar create(World world, CreateAvatarSpec spec) {
        spec.validate();
        world.validateEntry();

        Avatar avatar = new Avatar();
        avatar.worldId = world.getId();
        avatar.playerId = spec.playerId();
        avatar.avatarId = spec.avatarId();
        avatar.nickname = spec.nickname();

        return avatar;
    }

    public interface CreateAvatarSpec {
        PlayerId playerId();
        int avatarId();
        String nickname();

        default void validate() {
            if (playerId() == null) {
                throw new IllegalArgumentException("playerId is required");
            }
            if (avatarId() <= 0) {
                throw new IllegalArgumentException("AvatarId must be positive");
            }
            if (nickname() == null || nickname().isBlank()) {
                throw new IllegalArgumentException("Nickname is required");
            }
        }
    }
}