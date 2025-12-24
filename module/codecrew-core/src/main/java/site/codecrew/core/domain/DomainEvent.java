package site.codecrew.core.domain;

import java.time.Instant;
import java.util.UUID;

public abstract class DomainEvent {
    private final UUID eventId;
    private final Instant occurredAt;
    private final String aggregateType;
    private final String aggregateId;
    private final int version;

    protected DomainEvent(String aggregateType, String aggregateId, int version) {
        this.eventId = UUID.randomUUID();
        this.occurredAt = Instant.now();
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.version = version;
    }

}
