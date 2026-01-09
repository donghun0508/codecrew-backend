package site.codecrew.world.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.packet.payload.InitialSyncPayload;

@RequiredArgsConstructor
@Component
public class WorldSnapshotService {
    public Mono<InitialSyncPayload> loadSnapshot(String targetSessionId) {
        return Mono.just(new InitialSyncPayload(targetSessionId));
    }
}