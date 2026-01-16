package site.codecrew.world.domain.system;

import lombok.Getter;
import site.codecrew.r2dbc.jpa.AggregateRoot;
import site.codecrew.world.domain.system.AnnouncementEvent.AnnouncementUpdatedMessageEvent;

@Getter
public class Announcement extends AggregateRoot {

    private String message;

    public Announcement(String message) {
        this.message = message;
    }

    public void updateMessage(String message) {
        this.message = message;
        this.registerEvent(new AnnouncementUpdatedMessageEvent(this));
    }

    public static Announcement dummy() {
        return new Announcement(
            "CodeCrew World에 오신 것을 환영합니다! 🎉\n\n" +
                "아직 초기 개발 단계라 기능이 많이 없습니다... 😅\n" +
                "본 서버는 매일 00시에 종료되니 참고해 주세요!\n\n" +
                "오늘도 즐겁게 코딩합시다! 🚀"
        );
    }
}
