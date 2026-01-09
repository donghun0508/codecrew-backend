package site.codecrew.world.application;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.WorldSessionContext;

@Component
class DomainWorldEntryMetaVerificationPolicy implements WorldEntryMetaVerificationPolicy {

    @Override
    public Mono<Void> verify(WorldSessionContext context) {
        return Mono.fromRunnable(context::validate);
    }
}
