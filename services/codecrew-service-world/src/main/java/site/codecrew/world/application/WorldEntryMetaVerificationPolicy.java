package site.codecrew.world.application;

import reactor.core.publisher.Mono;
import site.codecrew.world.domain.WorldSessionContext;

public interface WorldEntryMetaVerificationPolicy {
    Mono<Void> verify(WorldSessionContext context);
}