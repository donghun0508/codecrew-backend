package site.codecrew.world.domain;

import java.util.function.Consumer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class StateHolder<T> {
    private T state;
    private final Sinks.Many<T> sink = Sinks.many().multicast().directBestEffort();

    public StateHolder(T initialBatch) {
        this.state = initialBatch;
    }

    public void update(T newState) {
        this.state = newState;
        this.sink.tryEmitNext(this.state);
    }

    public void mutate(Consumer<T> action) {
        action.accept(this.state);
        this.sink.tryEmitNext(this.state);
    }

    public T get() { return state; }
    public Flux<T> observe() { return sink.asFlux(); }
}