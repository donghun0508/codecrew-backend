package site.codecrew.jpa;

import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import site.codecrew.core.domain.DomainEvent;

@Component
public class DomainEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public DomainEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publish(AggregateRoot entity) {
        entity.pullDomainEvents().forEach(eventPublisher::publishEvent);
    }
}
