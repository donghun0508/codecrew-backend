package site.codecrew.jpa;

import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import site.codecrew.core.domain.DomainEvent;

@Component
public class DomainEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public DomainEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent(List<DomainEvent> events) {
        for (DomainEvent event : events) {
            applicationEventPublisher.publishEvent(event);
        }
    }
}
