package site.codecrew.world.domain.world;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
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
    private final AtomicInteger currentCapacity = new AtomicInteger(0);

    public void occupy() {
        this.currentCapacity.updateAndGet(current -> {
            if (current >= this.maxCapacity) {
                throw new IllegalStateException("월드가 가득 찼습니다.");
            }
            return current + 1;
        });
    }


    public void vacate() {
        this.currentCapacity.updateAndGet(current -> {
            if (current <= 0) {
                return 0;
            }
            return current - 1;
        });
    }

    public void assertCapacityAvailable() {
        if (this.currentCapacity.get() >= this.maxCapacity) {
            throw new WorldFullException(this.id);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        World world = (World) o;
        return Objects.equals(getId(), world.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "World{" +
            "id=" + id +
            ", maxCapacity=" + maxCapacity +
            ", name='" + name + '\'' +
            ", currentCapacity=" + currentCapacity +
            ", remainingCapacity=" + (maxCapacity - currentCapacity.get()) +
            '}';
    }
}