package site.codecrew.world.domain.packet.server.payload;

import java.util.Set;
import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.packet.Direction;
import site.codecrew.world.domain.packet.Packet;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.Payload;
import site.codecrew.world.domain.packet.client.payload.AvatarMoveClientPayload;
import site.codecrew.world.domain.packet.transmission.Multicast;
import site.codecrew.world.domain.packet.transmission.TransmissionOption;

public record AvatarMovedPayload(
    String playerId,
    int x,
    int y,
    Direction direction,
    boolean isMoving
) implements Payload, Multicast {

    @Override
    public Set<TransmissionOption> options() {
        return Set.of(TransmissionOption.EXCLUDE_SENDER);
    }

    public static Packet<AvatarMovedPayload> toPacket(Connection connection, AvatarMoveClientPayload moveClientPayload) {
        return Packet.of(PacketType.C_NET_AVATAR_MOVED_NOTIFY,
            connection.getId(),
            new AvatarMovedPayload(connection.getIdentityHash(), moveClientPayload.x(), moveClientPayload.y(), moveClientPayload.direction(), moveClientPayload.isMoving())
        );
    }
}
