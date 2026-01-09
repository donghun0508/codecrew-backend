package site.codecrew.world.domain.packet.payload;

public record InitialSyncPayload(
    String sessionId
) implements Payload   {

}
