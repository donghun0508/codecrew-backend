package site.codecrew.youtube.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.codecrew.jpa.BaseEntity;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
    name = "youtube_video",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_youtube_video_video_id", columnNames = "video_id")
    }
)
public class YoutubeVideo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "video_id", nullable = false, length = 16)
    private String videoId;

    @Column(name = "channel_id", nullable = false, length = 32)
    private String channelId;

    @Column(name = "title", length = 255)
    private String title;

    @Lob
    @Column(name = "description", columnDefinition = "MEDIUMTEXT")
    private String description;

    @Column(name = "thumbnail_url", length = 512)
    private String thumbnailUrl;

    @Column(name = "published_at", nullable = false)
    private Instant publishedAt;

    @Column(name = "is_shorts", nullable = false)
    private boolean shorts;

    @Column(name = "shorts_checked_at")
    private Instant shortsCheckedAt;

    @Column(name = "duration_seconds")
    private Integer durationSeconds;

    @Column(name = "definition", length = 8)
    private String definition;

    @Column(name = "caption")
    private Boolean caption;

    public static YoutubeVideo of(
        String videoId,
        String channelId,
        String title,
        String description,
        String thumbnailUrl,
        Instant publishedAt
    ) {
        YoutubeVideo v = new YoutubeVideo();
        v.videoId = videoId;
        v.channelId = channelId;
        v.title = title;
        v.description = description;
        v.thumbnailUrl = thumbnailUrl;
        v.publishedAt = publishedAt;
        v.shorts = false;
        v.shortsCheckedAt = null;
        return v;
    }

    public void markShorts(boolean isShorts, Instant checkedAt) {
        this.shorts = isShorts;
        this.shortsCheckedAt = checkedAt;
    }

    public void applyContentDetails(Integer durationSeconds, String definition, Boolean caption) {
        this.durationSeconds = durationSeconds;
        this.definition = definition;
        this.caption = caption;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }
}