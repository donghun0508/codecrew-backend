package site.codecrew.world.master.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.codecrew.jpa.AggregateRoot;
import site.codecrew.world.master.application.exception.WorldMasterErrorCode;
import site.codecrew.world.master.application.exception.WorldMasterException;

@Getter
@Entity
@Table(name = "world", uniqueConstraints = {
    @UniqueConstraint(name = "uk_world_code", columnNames = "code")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class World extends AggregateRoot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "world_id")
    private Long id;

    @Embedded
    private WorldCode code;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private int maxCapacity;

    @Column(nullable = false)
    private int currentCapacity = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_state", nullable = false, length = 20)
    private ServiceState serviceState;

    public void validateEntry() {
        if (!isOpen()) {
            throw new WorldMasterException(WorldMasterErrorCode.WORLD_NOT_AVAILABLE);
        }
        if (!hasSpace()) {
            throw new WorldMasterException(WorldMasterErrorCode.WORLD_FULL);
        }
    }

    private boolean isOpen() {
        return this.serviceState == ServiceState.OPEN;
    }

    private boolean hasSpace() {
        return this.currentCapacity < this.maxCapacity;
    }
}