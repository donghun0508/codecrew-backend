package site.codecrew.world.domain.player;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import site.codecrew.r2dbc.jpa.AggregateRoot;

@Getter
@Table("mission")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mission extends AggregateRoot {

    @Id
    @Column("mission_id")
    private Long id;

    @Column("player_id")
    private Long playerId;

    @Column("title")
    private String title;

    @Column("description")
    private String description;

    @Column("github_url")
    private String githubUrl;

    @Column("started_at")
    private LocalDateTime startedAt;

    @Column("ended_at")
    private LocalDateTime endedAt;

    @Column("mission_status")
    private MissionStatus missionStatus;

    public static Mission create(Long playerId, String title, String description, String githubUrl) {
        Mission mission = new Mission();
        mission.playerId = playerId;
        mission.title = title;
        mission.description = description;
        mission.githubUrl = githubUrl;
        mission.missionStatus = MissionStatus.PENDING;
        return mission;
    }

    public void start() {
        if (this.missionStatus != MissionStatus.PENDING) {
            throw new IllegalStateException("대기 중(PENDING)인 미션만 시작할 수 있습니다. 현재 상태: " + this.missionStatus);
        }
        this.startedAt = LocalDateTime.now();
        this.missionStatus = MissionStatus.PROCESSING;
    }

    public void pending() {
        if (this.missionStatus != MissionStatus.PROCESSING) {
            throw new IllegalStateException("진행 중(PROCESSING)인 미션만 대기 상태로 되돌릴 수 있습니다.");
        }
        this.startedAt = null;
        this.missionStatus = MissionStatus.PENDING;
    }

    public void complete() {
        if (this.missionStatus != MissionStatus.PROCESSING) {
            throw new IllegalStateException("진행 중(PROCESSING)인 미션만 완료 처리할 수 있습니다.");
        }
        this.endedAt = LocalDateTime.now();
        this.missionStatus = MissionStatus.COMPLETED;
    }
}
