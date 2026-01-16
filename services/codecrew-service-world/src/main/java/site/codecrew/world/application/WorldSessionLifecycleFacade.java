package site.codecrew.world.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Service
public class WorldSessionLifecycleFacade {

    private final WorldSessionFacade sessionFacade;
    private final WorldInboundMessageHandler worldInboundMessageHandler;
    private final WorldSessionMonitor sessionMonitor;

    public Mono<Void> onSessionEstablished(WorldEntryCommand command) {
        return Mono.deferContextual(ctx -> sessionFacade.enter(command)
            .flatMap(conn ->
                Mono.firstWithSignal(
                        worldInboundMessageHandler.handle(conn),
                        sessionMonitor.monitor(conn)
                    )
                    .doFinally(signal -> sessionFacade.leave(conn).subscribe())
            ));
    }
}

// 1. [Fast Fail] 월드 인원수만 살짝 확인 (증가 X)
// TODO: 메모리에 로딩된 게 있다면 꽉 찼는지 체크, 없으면 패스(DB 로딩은 나중에)
//        if (worldLocalRepository.isFull(worldId)) {
//            return Mono.error(new IllegalStateException("월드가 꽉 찼습니다."));
//        }