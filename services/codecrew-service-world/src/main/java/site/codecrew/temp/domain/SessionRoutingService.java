package site.codecrew.world.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.repository.SessionRoutingRepository;

@RequiredArgsConstructor
@Service
public class SessionRoutingService {

    private final SessionRoutingRepository sessionRoutingRepository;

    public Mono<Void> saveSessionRouting(SessionRouting sessionRouting) {
        return sessionRoutingRepository.save(sessionRouting).then();
    }

    public Mono<Void> removeSessionRouting(String sessionId) {
        return sessionRoutingRepository.deleteById(sessionId).then();
    }
}
