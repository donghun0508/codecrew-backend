package site.codecrew.world.infrastructure;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import reactor.core.publisher.Mono;

public class MemoryStore<T, ID> {

    private final Map<ID, T> map;
    private final Function<T, ID> idExtractor;

    public MemoryStore(Map<ID, T>  map, Function<T, ID> idExtractor) {
        this.map = map;
        this.idExtractor = idExtractor;
    }

    public Mono<T> save(T entity) {
        return Mono.fromCallable(() -> {
            ID id = idExtractor.apply(entity);
            map.put(id, entity);
            return entity;
        });
    }

    public Mono<T> findById(ID id) {
        return Mono.justOrEmpty(map.get(id));
    }

    public Mono<Void> deleteById(ID id) {
        return Mono.fromRunnable(() -> map.remove(id));
    }

    public Mono<Boolean> existsById(ID id) {
        return Mono.just(map.containsKey(id));
    }

    public Mono<Boolean> saveIfAbsent(T entity) {
        return Mono.fromCallable(() -> {
            ID id = idExtractor.apply(entity);
            return map.putIfAbsent(id, entity) == null;
        });
    }

    public Mono<Boolean> updateIf(ID id, Predicate<T> condition, Consumer<T> action) {
        return Mono.fromCallable(() -> {
            var result = new Object() { boolean success = false; };

            map.computeIfPresent(id, (key, entity) -> {
                if (condition.test(entity)) {
                    action.accept(entity);
                    result.success = true;
                }
                return entity;
            });

            return result.success;
        });
    }
}