package site.codecrew.jpa.event.outbox;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "outbox", indexes = {
    @Index(name = "idx_outbox_created_at", columnList = "createdAt")
})
public class OutboxEntity implements Persistable<String> {

    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "aggregate_type", nullable = false, length = 50)
    private String aggregateType;

    @Column(name = "aggregate_id", nullable = false, length = 50)
    private String aggregateId;

    @Column(name = "event_type", nullable = false, length = 100)
    private String eventType;

    @Column(columnDefinition = "json", nullable = false)
    private String payload;

    @Column(name = "trace_id", length = 32)
    private String traceId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    public static OutboxEntity from(String id, String aggType, String aggId, String eventType, String payload, String traceId) {
        return OutboxEntity.builder()
            .id(id)
            .aggregateType(aggType.toLowerCase())
            .aggregateId(aggId)
            .eventType(eventType)
            .payload(payload)
            .traceId(traceId)
            .createdAt(LocalDateTime.now())
            .build();
    }
}