package site.codecrew.jpa.event.inbox;

import site.codecrew.core.domain.DomainEvent;

public abstract class AbstractEventHandler<T extends DomainEvent> implements EventHandler<T> {

    private final String eventType;
    private final Class<T> eventClass;

    protected AbstractEventHandler(String eventType, Class<T> eventClass) {
        this.eventType = eventType;
        this.eventClass = eventClass;
    }

    @Override
    public String getEventType() {
        return eventType;
    }

    @Override
    public Class<T> getEventClass() {
        return eventClass;
    }
}