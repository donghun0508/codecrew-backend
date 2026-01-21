package site.codecrew.world.domain.packet;

public enum PacketType {

    // [1] 월드 진입/퇴장 (Lifecycle)
    C_NET_WORLD_ENTER_RES,          // (S->C) 접속/입장 결과(내 정보, 초기 데이터)
    C_NET_PLAYER_JOINED_NOTIFY,     // (S->C) 플레이어 입장 알림 (broadcast)
    C_NET_PLAYER_LEFT_NOTIFY,       // (S->C) 플레이어 퇴장 알림 (broadcast)

    // [2] 이동/위치 (Movement)
    C_NET_AVATAR_MOVE_REQ,          // (C->S) 아바타 이동 요청
    C_NET_AVATAR_MOVED_NOTIFY,      // (S->C) 아바타 이동 동기화 (broadcast)

    // [3] 상호작용 (Interaction)
    C_NET_CHAT_SEND_REQ,            // (C->S) 채팅 전송
    C_NET_CHAT_MESSAGE_NOTIFY,      // (S->C) 채팅 메시지 전파 (broadcast)

    // [4] 미션 (Mission)
    C_NET_MISSION_REGISTER_REQ,     // (C->S) 미션 등록 요청
    C_NET_MISSION_DELETE_REQ,       // (C->S) 미션 삭제 요청
    C_NET_MISSION_DELETE_ALL_REQ,   // (C->S) 미션 전체 삭제 요청
    C_NET_MISSION_STATUS_CHANGE_REQ,        // (C->S) 미션 시작 요청
    C_NET_MISSION_REGISTERED_NOTIFY, // (S->C) 미션 등록 알림 (broadcast)
    C_NET_MISSION_DELETED_ALL_NOTIFY,
    C_NET_MISSION_DELETED_NOTIFY,   // (S->C) 미션 삭제 알림 (broadcast)
    C_NET_MISSION_STATUS_CHANGED_NOTIFY,  // (S->C) 미션 시작 알림 (broadcast)

    C_NET_NUDGE_REQ,    // (C->S) 알림 요청
    C_NET_NUDGE_NOTIFY, // (S->C)


    // [5] 시스템 (System)
    C_NET_SYSTEM_ALERT_NOTIFY,      // (S->C) 시스템 알림(경고/에러 포함)
    C_NET_SYSTEM_ERROR_NOTIFY       // (S->C) 에러 알림
}
