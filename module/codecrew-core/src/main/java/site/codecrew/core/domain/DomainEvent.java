package site.codecrew.core.domain;

import java.time.Instant;
import java.util.UUID;

public abstract class DomainEvent {
    private final UUID eventId;
    private final Instant occurredAt;
    private final String aggregateType;
    private final String aggregateId;
    private final String version;

    protected DomainEvent(String aggregateType, String aggregateId, String version) {
        this.eventId = UUID.randomUUID();
        this.occurredAt = Instant.now();
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.version = version;
    }

    protected DomainEvent() {
        this.eventId = UUID.randomUUID();
        this.occurredAt = Instant.now();
        this.aggregateType = null;
        this.aggregateId = null;
        this.version = null;
    }

}
