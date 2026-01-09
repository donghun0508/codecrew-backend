package site.codecrew.world.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LocalRepository <T, ID> {

    Mono<T> save(T entity);

    Mono<Boolean> saveIfAbsent(T entity);

    Mono<T> findById(ID id);

    Flux<T> findAllById(ID id);

    Flux<T> findAllByIds(Iterable<ID> ids);

    Mono<Void> delete(T entity);

    Mono<Void> deleteById(ID id);

    Mono<Boolean> existsById(ID id);
}
