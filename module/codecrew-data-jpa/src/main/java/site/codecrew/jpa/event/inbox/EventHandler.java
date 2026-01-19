package site.codecrew.jpa.event.inbox;


import site.codecrew.core.domain.DomainEvent;

public interface EventHandler<T extends DomainEvent> {

    String getEventType();

    Class<T> getEventClass();

    void handle(T event);
}