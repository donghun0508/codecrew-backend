package site.codecrew.jpa.event.outbox;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import site.codecrew.core.domain.DomainEvent;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class OutboxEventListener {

    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleDomainEvent(DomainEvent event) {
        OutboxEntity outbox = OutboxEntity.from(event.getEventId().toString(), event.getAggregateType(), event.getAggregateId(), event.getClass().getSimpleName(), toJson(event), null);
        outboxRepository.save(outbox);
    }

    private String toJson(Object object) {
        return objectMapper.writeValueAsString(object);
    }
}