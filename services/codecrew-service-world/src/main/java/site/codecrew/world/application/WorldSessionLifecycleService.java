package site.codecrew.world.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.WorldSessionContext;
import site.codecrew.world.domain.WorldSessionManager;

@RequiredArgsConstructor
@Component
public class WorldSessionLifecycleService {

    private final WorldSessionManager sessionManager;
    private final WorldEntryMetaValidator metaValidator;
    private final WorldInitialSnapshotSender snapshotLoader;
    private final WorldInboundMessageHandler messageHandler;

    public Mono<Void> onSessionEstablished(WorldSessionContext context) {
        return sessionManager.initializeSession(context)
            .then(Mono.defer(() -> metaValidator.validate(context)))
            .then(Mono.defer(() -> sessionManager.register(context)))
            .then(Mono.defer(() -> snapshotLoader.sendBootstrapSnapshot(context)))
            .then(Mono.defer(() -> messageHandler.start(context)))
            .doFinally(st -> sessionManager.release(context).subscribe());
    }
}


// 데이터 조회
// (월드, 회원 데이터 검증) 데이터 검증
// 데이터 저장
    // 초기 데이터 조회 및 전송 (server to client unicast)
    // .then(snapshotLoader.load(sessionContext))
    // 입장 브로드 캐스팅 (server to client broadcast)
    // 메시지 리스너 시작 (client to server unicast)
    // 모니터링 시작
// 자원 청소