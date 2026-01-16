package site.codecrew.world.domain.packet.server.payload;

import java.util.List;
import java.util.Set;
import site.codecrew.world.domain.packet.Packet;
import site.codecrew.world.domain.packet.PacketType;
import site.codecrew.world.domain.packet.Payload;
import site.codecrew.world.domain.packet.model.PlayerModel;
import site.codecrew.world.domain.packet.transmission.Multicast;
import site.codecrew.world.domain.packet.transmission.TransmissionOption;
import site.codecrew.world.domain.player.Mission;
import site.codecrew.world.domain.player.Player;
import site.codecrew.world.domain.player.PlayerWithMission;

public record WorldPlayerJoinPayload(
    PlayerModel player
) implements Payload, Multicast {

    @Override
    public Set<TransmissionOption> options() {
        return Set.of(TransmissionOption.EXCLUDE_SENDER);
    }

    public static Packet<WorldPlayerJoinPayload> toPacket(
        String sessionId,
        Player player,
        List<Mission> missions
    ) {
        WorldPlayerJoinPayload payload = new WorldPlayerJoinPayload(PlayerModel.from(
            new PlayerWithMission(player, missions)
        ));
        return Packet.of(PacketType.C_NET_PLAYER_JOINED_NOTIFY, sessionId, payload);
    }
}
