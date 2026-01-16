package site.codecrew.world.domain;

import reactor.core.publisher.Mono;

public interface LocalRepository<T, ID> {

    Mono<T> save(T entity);

    Mono<T> findById(ID id);

    Mono<Boolean> saveIfAbsent(T entity);

    Mono<Boolean> existsById(ID id);

    Mono<Void> deleteById(ID id);
}
