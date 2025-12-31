package site.codecrew.world.master.domain;

public enum ServiceState {
    /** 점검 중 (관리자만 접속 가능하거나 아예 차단) */
    MAINTENANCE,

    /** 정상 운영 (입장 가능) */
    OPEN,

    /** 신규 입장 제한 (기존 접속자만 유지) */
    LOCKED,

    /** 서비스 종료 또는 비활성화 */
    CLOSED,

    /** 배포 중 (서버 기동 단계) */
    DEPLOYING
}
