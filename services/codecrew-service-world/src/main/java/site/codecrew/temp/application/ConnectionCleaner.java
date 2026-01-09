package site.codecrew.world.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.PlayerConnectionService;
import site.codecrew.world.domain.SessionRoutingService;
import site.codecrew.world.domain.WorldService;

@Slf4j
@RequiredArgsConstructor
@Component
public class ConnectionCleaner {

    private final PlayerConnectionService playerConnectionService;
    private final SessionRoutingService sessionRoutingService;
    private final WorldService worldService;


    public Mono<Void> cleanup(WorldSession worldSession) {
        String sessionId = worldSession.sessionId();
        Long worldId = worldSession.worldId(); // WorldSession에 worldId가 있다고 가정

        // 순서대로 실행:
        // 1. 플레이어 연결 정보 삭제
        // 2. 라우팅 정보 삭제
        // 3. 월드 인원수 감소
        return playerConnectionService.cleanup(sessionId)
            .then(sessionRoutingService.removeSessionRouting(sessionId))
            .then(worldService.deleteById(worldId))
            // 혹시 중간에 하나가 실패해도 나머지는 실행하고 싶다면 onErrorResume를 중간중간 넣어야 하지만,
            // 기본적으로는 이렇게 체이닝하면 됨.
            .then();
    }
}


// 리소스 청소하는법이 필요해보인다?
// playerId 관련된거 있으면 그냥 다 지워버리면 될거같은데,,