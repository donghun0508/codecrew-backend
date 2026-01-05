package site.codecrew.world.master.infrastructure;

import java.util.Optional;

public interface WorldRedisRepository<D> {

    void set(String key, D data);
    Optional<D> find(String key);
    void delete(String key);
}
