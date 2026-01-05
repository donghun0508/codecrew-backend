package site.codecrew.world.domain.message;

public enum MessageType {
    CHAT,           // 일반 채팅
    SYSTEM_NOTICE,  // 시스템 공지
    PLAYER_JOIN,    // 플레이어 입장 알림
    PLAYER_LEAVE,    // 플레이어 퇴장 알림
    INITIAL_SYNC,  // 초기 동기화 메시지
}