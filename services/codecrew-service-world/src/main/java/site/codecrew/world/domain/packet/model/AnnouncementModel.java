package site.codecrew.world.domain.packet.model;

import site.codecrew.world.domain.system.Announcement;

public record AnnouncementModel(
    String message
) {

    public static AnnouncementModel from(Announcement announcement) {
        return new AnnouncementModel(announcement.getMessage());
    }
}
