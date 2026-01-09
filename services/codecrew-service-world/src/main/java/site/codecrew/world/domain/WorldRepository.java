package site.codecrew.world.domain;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface WorldRepository extends R2dbcRepository<World, Long> {

}
