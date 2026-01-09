package site.codecrew.world.domain.message;

public record ClientChatRequest(
    String message,
    String chatAreaType
) {}