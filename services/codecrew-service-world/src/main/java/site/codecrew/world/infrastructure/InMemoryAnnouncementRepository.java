package site.codecrew.world.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.codecrew.world.domain.system.AnnouncementRepository;

@RequiredArgsConstructor
@Repository
class InMemoryAnnouncementRepository implements AnnouncementRepository {

}
