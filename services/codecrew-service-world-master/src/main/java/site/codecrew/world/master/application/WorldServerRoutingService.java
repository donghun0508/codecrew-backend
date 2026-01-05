package site.codecrew.world.master.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.codecrew.world.master.domain.ServerNode;
import site.codecrew.world.master.domain.World;
import site.codecrew.world.master.domain.WorldCode;
import site.codecrew.world.master.domain.WorldServerNodeService;
import site.codecrew.world.master.domain.WorldService;

@RequiredArgsConstructor
@Component
public class WorldServerRoutingService {
    private final WorldServerNodeService serverNodeService;
    private final WorldServerFlexMatchEngine matchEngine;

    public ServerNode route(World world) {
        // Phase 1. 등기소 조회 -> 없으면(Empty) orElseGet 내부 로직 실행
        return serverNodeService.findByWorldId(world.getId())
            .orElseGet(() -> {
                // 2. [핵심] 매칭 + 저장 (Phase 2,3,4)
                // 동시성 보장을 위해 '저장(save)'까지 Engine이 락(Lock) 안에서 처리해야 안전함
                return matchEngine.match(world);
            });
    }
}
