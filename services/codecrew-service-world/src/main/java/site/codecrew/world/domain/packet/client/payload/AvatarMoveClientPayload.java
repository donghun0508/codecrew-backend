package site.codecrew.world.domain.packet.client.payload;

import site.codecrew.world.domain.packet.Direction;
import site.codecrew.world.domain.packet.Payload;

public record AvatarMoveClientPayload(
    int x,
    int y,
    Direction direction,
    boolean isMoving
) implements Payload {

}
