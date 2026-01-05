package site.codecrew.world.master.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.codecrew.data.redis.lock.DistributedLock;
import site.codecrew.world.master.domain.ServerNode;
import site.codecrew.world.master.domain.World;
import site.codecrew.world.master.domain.WorldServerNodeService;
import site.codecrew.world.master.domain.node.WorldServerLoadStore;

@RequiredArgsConstructor
@Component
public class WorldServerFlexMatchEngine {

    @Value("${app.agones.ingress-domain}")
    private String ingressDomain;
    @Value("${app.agones.use-ingress:false}")
    private boolean useIngress;

    private final WorldServerNodeService serverNodeService;
    private final WorldServerLoadStore loadStore;
    private final WorldServerAllocator serverAllocator;

    @DistributedLock(key = "'world:' + #world.id + ':match'")
    public ServerNode match(World world) {
        // 1. [Double Check] 락 대기 중에 누가 만들었는지 확인
        Optional<ServerNode> exists = serverNodeService.findByWorldId(world.getId());
        if (exists.isPresent()) {
            return exists.get(); // 이미 있으면 저장할 필요 없이 리턴
        }

        int requiredCapacity = world.getMaxCapacity();
        ServerNode selectedNode;

        // 2. [Phase 2] 재사용 시도
        Optional<ServerNode> reusableNodeOpt = loadStore.tryReuseServer(requiredCapacity);

        if (reusableNodeOpt.isPresent()) {
            // 재사용 성공
            selectedNode = reusableNodeOpt.get();
        } else {
            // 3. [Phase 3] 신규 할당
            selectedNode = serverAllocator.allocateNewServer();
            // Redis 용량 점수판 등록
            loadStore.registerNewNode(selectedNode, requiredCapacity);
        }

        // 4. [Phase 4] 최종 매핑 저장 (핵심!)
        // 락이 풀리기 전에 "이 월드는 이 노드에 있다"고 박제해야 함.
        ServerNode savedServerNode = generateUrl(selectedNode);
        serverNodeService.save(world.getId(), savedServerNode);
        return savedServerNode;
    }

    private ServerNode generateUrl(ServerNode serverNode) {
        String connectionUrl;

        if (useIngress) {
            // [운영] Octops 방식: 도메인 + "/" + 파드ID(이름)
            // 예: wss://game.codecrew.site/server-abc
            connectionUrl = String.format("%s/%s", ingressDomain, serverNode.id());
        } else {
            connectionUrl = ingressDomain;
        }

        return new ServerNode(serverNode.id(), connectionUrl);
    }
}