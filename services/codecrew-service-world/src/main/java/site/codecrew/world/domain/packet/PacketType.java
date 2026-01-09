package site.codecrew.world.domain.packet;

public enum PacketType {

    // [1] 월드 진입/퇴장 (Lifecycle)
    C_NET_WORLD_ENTER_RES,          // (S->C) 접속/입장 결과(내 정보, 초기 데이터)
    C_NET_AVATAR_JOINED_NOTIFY,     // (S->C) 아바타 입장 알림 (broadcast)
    C_NET_AVATAR_LEFT_NOTIFY,       // (S->C) 아바타 퇴장 알림 (broadcast)

    // [2] 이동/위치 (Movement)
    C_NET_AVATAR_MOVE_REQ,          // (C->S) 아바타 이동 요청
    C_NET_AVATAR_MOVE_RES,          // (S->C) 이동 승인/보정(필요 시)
    C_NET_AVATAR_MOVED_NOTIFY,      // (S->C) 아바타 이동 동기화 (broadcast)

    // [3] 상호작용 (Interaction)
    C_NET_CHAT_SEND_REQ,            // (C->S) 채팅 전송
    C_NET_CHAT_MESSAGE_NOTIFY,      // (S->C) 채팅 메시지 전파 (broadcast)

    // [4] 시스템 (System)
    C_NET_SYSTEM_ALERT_NOTIFY,      // (S->C) 시스템 알림(경고/에러 포함)
    C_NET_SYSTEM_ERROR_NOTIFY       // (S->C) 에러 알림
}
