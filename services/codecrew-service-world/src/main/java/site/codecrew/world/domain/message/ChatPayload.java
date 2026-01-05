package site.codecrew.world.domain.message;

public record ChatPayload(
    String message,
    SenderInfo sender
) {}