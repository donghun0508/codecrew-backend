package site.codecrew.world.domain.packet.server.payload;

import site.codecrew.world.domain.connection.Connection;
import site.codecrew.world.domain.packet.Packet;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.Payload;
import site.codecrew.world.domain.packet.model.PlayerSimpleModel;
import site.codecrew.world.domain.packet.transmission.Unicast;
import site.codecrew.world.domain.player.Player;

public record NudgePayload(
    PlayerSimpleModel sourcePlayerId
) implements Payload, Unicast {

    public static Packet<NudgePayload> toPacket(Connection connection, Player player) {
        return Packet.of(PacketType.C_NET_NUDGE_NOTIFY,
            connection.getId(),
            new NudgePayload(PlayerSimpleModel.from(player))
        );
    }
}
