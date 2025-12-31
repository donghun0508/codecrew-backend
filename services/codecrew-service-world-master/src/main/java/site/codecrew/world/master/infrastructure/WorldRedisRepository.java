package site.codecrew.world.master.infrastructure;

import java.util.Optional;

public interface WorldRedisRepository<T> {

    void set(String key, T data);
    Optional<T> find(String key);
    void delete(String key);
}
