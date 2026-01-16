package site.codecrew.world.domain.packet.model;

import site.codecrew.world.domain.system.Announcement;

public record SystemModel(
    AnnouncementModel announcement
) {

    public static SystemModel from(Announcement announcement) {
        return new SystemModel(
            AnnouncementModel.from(announcement)
        );
    }
}
