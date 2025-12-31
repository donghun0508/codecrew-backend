package site.codecrew.world.master.infrastructure;

import io.fabric8.kubernetes.api.model.GenericKubernetesResource;
import io.fabric8.kubernetes.api.model.GenericKubernetesResourceBuilder;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.kubernetes.client.dsl.base.ResourceDefinitionContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.codecrew.world.master.infrastructure.AgonesWorldServerAllocator.Allocation;

@Slf4j
@Component
class KubernetesAgonesAllocationClient implements AgonesAllocationClient {

    private final KubernetesClient client;

    @Value("${agones.namespace}")
    private String namespace;

    KubernetesAgonesAllocationClient() {
        this.client = new KubernetesClientBuilder().build();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Allocation allocateForFleet(String fleetName) {
        ResourceDefinitionContext ctx = new ResourceDefinitionContext.Builder()
            .withGroup("allocation.agones.dev")
            .withVersion("v1")
            .withKind("GameServerAllocation")
            .withPlural("gameserverallocations")
            .withNamespaced(true)
            .build();

        Map<String, Object> selector = new HashMap<>();
        selector.put("matchLabels", Map.of("agones.dev/fleet", fleetName));

        Map<String, Object> spec = new HashMap<>();
        spec.put("required", selector);
        spec.put("scheduling", "Packed");

        GenericKubernetesResource gsa = new GenericKubernetesResourceBuilder()
            .withApiVersion("allocation.agones.dev/v1")
            .withKind("GameServerAllocation")
            .withMetadata(new ObjectMetaBuilder().withGenerateName("alloc-").build())
            .addToAdditionalProperties("spec", spec)
            .build();

        GenericKubernetesResource created = client.genericKubernetesResources(ctx)
            .inNamespace(namespace)
            .resource(gsa)
            .create();

        Map<String, Object> status = (Map<String, Object>) created.getAdditionalProperties().get("status");
        if (status == null) {
            throw new IllegalStateException("ALLOCATION_FAILED_NO_STATUS");
        }

        String state = (String) status.get("state");
        if (!"Allocated".equalsIgnoreCase(state)) {
            throw new IllegalStateException("ALLOCATION_FAILED_STATE_" + state);
        }

        String gameServerName = (String) status.get("gameServerName");
        String address = (String) status.get("address");
        List<Map<String, Object>> ports = (List<Map<String, Object>>) status.get("ports");
        
        if (gameServerName == null || address == null || ports == null || ports.isEmpty()) {
            throw new IllegalStateException("ALLOCATION_INVALID_DATA");
        }

        int port = ((Number) ports.getFirst().get("port")).intValue();

        return new Allocation(gameServerName, address, port);
    }
}