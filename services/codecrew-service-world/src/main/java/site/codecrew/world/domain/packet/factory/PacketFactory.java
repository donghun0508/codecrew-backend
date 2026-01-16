package site.codecrew.world.domain.packet.factory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.packet.Packet;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.server.PacketProvider;

@Component
public class PacketFactory {

    private final Map<PacketType, PacketProvider> providerMap;

    public PacketFactory(List<PacketProvider> providers) {
        this.providerMap = providers.stream()
            .collect(Collectors.toMap(PacketProvider::packetType, p -> p));
    }

    public Mono<Packet<?>> create(PacketType type, Connection connection) {
        PacketProvider provider = providerMap.get(type);
        if (provider == null) {
            return Mono.empty();
        }
        return provider.create(connection);
    }
}