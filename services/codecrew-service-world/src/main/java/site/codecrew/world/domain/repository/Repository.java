package site.codecrew.world.domain.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface Repository<T, ID> {

    Mono<T> save(T entity);

    Mono<T> findById(ID id);

    Flux<T> findAllById(ID id);

    Flux<T> findAllByIds(Iterable<ID> ids);

    Mono<Void> deleteById(ID id);

    Mono<Boolean> existsById(ID id);
}