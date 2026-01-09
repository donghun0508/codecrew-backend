package site.codecrew.world.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.WorldSessionContext;

@Slf4j
@RequiredArgsConstructor
@Component
public class WorldEntryMetaValidator {

    private final List<WorldEntryMetaVerificationPolicy> entryPolicies;

    public Mono<Void> validate(WorldSessionContext context) {
        return Flux.fromIterable(entryPolicies)
            .concatMap(policy -> policy.verify(context))
            .then();
    }
}
