package site.codecrew.world.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.domain.WorldSessionContext;
import site.codecrew.world.domain.WorldSnapshotService;
import site.codecrew.world.domain.packet.PacketSender;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.ServerPacket;

@RequiredArgsConstructor
@Component
public class WorldInitialSnapshotSender {

    private final PacketSender packetSender;
    private final WorldSnapshotService worldSnapshotService;

    public Mono<Void> sendBootstrapSnapshot(WorldSessionContext sessionContext) {
        String targetSessionId = sessionContext.session().id();

        return worldSnapshotService.loadSnapshot(targetSessionId)
            .map(snapshot -> ServerPacket.success(PacketType.C_NET_WORLD_ENTER_RES, snapshot))
            .flatMap(packet -> packetSender.unicast(targetSessionId, packet));
    }
}