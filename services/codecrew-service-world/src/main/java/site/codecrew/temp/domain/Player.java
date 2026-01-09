package site.codecrew.world.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@Table("player")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Player {

    @Id
    @Column("player_id")
    private Long id;

    @Column("world_id")
    private long worldId;

    @Column("public_player_id")
    private String publicPlayerId;

    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private Avatar avatar;

    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private Location location;

    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private PlayerAttribute attribute;

}