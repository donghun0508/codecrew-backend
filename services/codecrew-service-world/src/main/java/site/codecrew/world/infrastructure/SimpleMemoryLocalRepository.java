package site.codecrew.world.infrastructure;

import java.util.Map;
import java.util.function.Function;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.LocalRepository;

public abstract class SimpleMemoryLocalRepository<T, ID> implements LocalRepository<T, ID> {

    protected final MemoryStore<T, ID> store;
    private final Function<T, ID> idExtractor;

    protected SimpleMemoryLocalRepository(Map<ID, T> map, Function<T, ID> idExtractor) {
        this.store = new MemoryStore<>(map, idExtractor);
        this.idExtractor = idExtractor;
    }

    @Override
    public Mono<T> save(T entity) {
        return store.save(entity);
    }

    @Override
    public Mono<Boolean> saveIfAbsent(T entity) {
        return store.saveIfAbsent(entity);
    }

    @Override
    public Mono<T> findById(ID id) {
        return store.findById(id);
    }

    @Override
    public Mono<Void> deleteById(ID id) {
        return store.deleteById(id);
    }

    @Override
    public Mono<Boolean> existsById(ID id) {
        return store.existsById(id);
    }
}