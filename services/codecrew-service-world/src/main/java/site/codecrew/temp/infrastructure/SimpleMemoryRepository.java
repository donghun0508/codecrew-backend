package site.codecrew.world.infrastructure;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.repository.Repository;

public abstract class SimpleMemoryRepository<T, ID> implements Repository<T, ID> {

    protected final MemoryStore<T, ID> store;

    protected SimpleMemoryRepository(Map<ID, T> map, Function<T, ID> idExtractor) {
        this.store = new MemoryStore<>(map, idExtractor);
    }

    @Override
    public Mono<T> save(T entity) {
        return store.save(entity);
    }

    @Override
    public Mono<T> findById(ID id) {
        return store.findById(id);
    }

    @Override
    public Flux<T> findAllById(ID id) {
        return findById(id).flux();
    }

    @Override
    public Flux<T> findAllByIds(Iterable<ID> ids) {
        return Flux.fromIterable(ids)
            .flatMap(store::findById);
    }

    @Override
    public Mono<Void> deleteById(ID id) {
        return store.deleteById(id);
    }

    public Mono<Boolean> existsById(ID id) {
        return store.existsById(id);
    }

    protected Mono<Boolean> doAtomicUpdate(ID id, Predicate<T> condition, Consumer<T> action) {
        return store.updateIf(id, condition, action);
    }
}