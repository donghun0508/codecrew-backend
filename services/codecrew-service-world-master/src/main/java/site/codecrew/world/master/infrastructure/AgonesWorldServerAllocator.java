package site.codecrew.world.master.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.codecrew.world.master.application.WorldServerAllocator;
import site.codecrew.world.master.domain.ServerNode;

@RequiredArgsConstructor
@Component
class AgonesWorldServerAllocator implements WorldServerAllocator {

    private final AgonesAllocationClient agonesAllocationClient;
    @Value("${app.agones.fleet-name}")
    private String targetFleetName;

    @Override
    public ServerNode allocateNewServer() {
        // 1. Agones Client 호출 (K8s API 통신)
        Allocation allocation = agonesAllocationClient.allocateForFleet(targetFleetName);
        return allocation.toServerNode();
    }

    public record Allocation(
        String gameServerName,
        String address,
        int port
    ) {
        public ServerNode toServerNode() {
            return new ServerNode(gameServerName, address);
        }
    }
}
