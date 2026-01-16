package site.codecrew.world.temp.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class World {

    @Id
    @Column("world_id")
    private Long id;

    @Column("max_capacity")
    private int maxCapacity;

    @Column("name")
    private String name;

    @Column("host")
    private String host;

    @Column("description")
    private String description;

    @Transient
    private int currentCapacity;

    public void assertEnterable() {

    }

    public void increateCurrentPlayer() {
        this.currentCapacity++;
    }

    public void decrementCurrentPlayer() {
        this.currentCapacity--;
    }
}