package site.codecrew.world.domain.packet.model;

import java.time.LocalDateTime;
import site.codecrew.world.domain.player.Mission;
import site.codecrew.world.domain.player.MissionStatus;

public record MissionModel(
    long id,
    long playerId,
    String title,
    String description,
    String githubUrl,
    LocalDateTime startedAt,
    LocalDateTime endedAt,
    LocalDateTime createdAt,
    MissionStatus missionStatus
) {

    public static MissionModel from(Mission mission) {
        return new MissionModel(
            mission.getId(),
            mission.getPlayerId(),
            mission.getTitle(),
            mission.getDescription(),
            mission.getGithubUrl(),
            mission.getStartedAt(),
            mission.getEndedAt(),
            mission.getCreatedAt(),
            mission.getMissionStatus()
        );
    }
}
