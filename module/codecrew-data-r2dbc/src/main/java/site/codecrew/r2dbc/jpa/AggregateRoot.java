package site.codecrew.r2dbc.jpa;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.data.annotation.Transient;
import site.codecrew.core.domain.DomainEvent;

@Getter
public abstract class AggregateRoot extends BaseEntity {

    @Transient
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    protected void registerEvent(DomainEvent event) {
        this.domainEvents.add(event);
    }

    public List<DomainEvent> flushEvents() {
        List<DomainEvent> events = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return events;
    }
}