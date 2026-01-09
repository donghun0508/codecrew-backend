package site.codecrew.world.domain.routing;

public enum MessagePublisherType {
    PRIVATE_AREA, // 현재 ROOM이면 ROOM, 아니면 현재 MAP에만
    PUBLIC_AREA   // MAP 범위: MAP + 하위 ROOM 전부
}