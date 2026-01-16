package site.codecrew.world.temp.domain.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import site.codecrew.world.temp.domain.World;

public interface WorldRepository extends R2dbcRepository<World, Long> {

}
