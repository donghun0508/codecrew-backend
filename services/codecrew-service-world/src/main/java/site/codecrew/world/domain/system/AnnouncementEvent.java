package site.codecrew.world.domain.system;

import site.codecrew.core.domain.DomainEvent;

public class AnnouncementEvent {

    public static class AnnouncementUpdatedMessageEvent extends DomainEvent {

        private final String message;

        protected AnnouncementUpdatedMessageEvent(Announcement announcement) {
            super(announcement.getClass().getTypeName(), null);
            this.message = announcement.getMessage();
        }
    }

}
