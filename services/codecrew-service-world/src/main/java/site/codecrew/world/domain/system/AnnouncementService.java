package site.codecrew.world.domain.system;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public Mono<Announcement> fetchDefault() {
        return Mono.just(Announcement.dummy());
    }
}
