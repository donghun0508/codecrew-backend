package site.codecrew.world.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Slf4j
@Component
public class EventBus {
    private final Sinks.Many<Object> sink = Sinks.many().multicast().onBackpressureBuffer();

    public void publish(Object event) {
        log.debug("[EventBus] Publishing: {}", event.getClass().getName());
        sink.tryEmitNext(event);
    }

    public <T> Flux<T> on(Class<T> eventType) {
        return sink.asFlux().ofType(eventType);
    }
}