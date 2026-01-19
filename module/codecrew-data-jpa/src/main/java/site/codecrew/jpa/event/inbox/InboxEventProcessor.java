package site.codecrew.jpa.event.inbox;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import site.codecrew.core.domain.DomainEvent;import tools.jackson.databind.ObjectMapper;

@Component
public class InboxEventProcessor {

    private final InboxRepository inboxRepository;
    private final ObjectMapper objectMapper;
    private final Map<String, EventHandler<? extends DomainEvent>> handlerMap;

    public InboxEventProcessor(
        InboxRepository inboxRepository,
        ObjectMapper objectMapper,
        List<EventHandler<? extends DomainEvent>> handlers
    ) {
        this.inboxRepository = inboxRepository;
        this.objectMapper = objectMapper;
        this.handlerMap = handlers.stream()
            .collect(Collectors.toMap(
                EventHandler::getEventType,
                Function.identity()
            ));
    }

    @Transactional
    public void process(String eventId, String eventType, String payload) {
        if (!tryMarkAsProcessed(eventId)) {
            return;
        }

        dispatch(eventType, payload);
    }

    @SuppressWarnings("unchecked")
    private <T extends DomainEvent> void dispatch(String eventType, String payload) {
        EventHandler<T> handler = (EventHandler<T>) handlerMap.get(eventType);
        if (handler == null) {
            throw new IllegalArgumentException("Unknown eventType: " + eventType);
        }

        T event = deserialize(payload, handler.getEventClass());
        handler.handle(event);
    }

    private boolean tryMarkAsProcessed(String eventId) {
        try {
            inboxRepository.save(new InboxEntity(eventId));
            return true;
        } catch (DataIntegrityViolationException e) {
            return false;
        }
    }

    @SneakyThrows
    private <T> T deserialize(String payload, Class<T> clazz) {
        return objectMapper.readValue(payload, clazz);
    }
}