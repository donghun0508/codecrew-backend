package site.codecrew.world.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("world")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class World {

    @Id
    @Column("world_id")
    private Long id;

    @Column("max_capacity")
    private int maxCapacity;

    @Transient
    private int currentCapacity;

    public void increateCurrentPlayer() {
        currentCapacity++;
    }

    public void decrementCurrentPlayer() {
        currentCapacity--;
    }
}
