package site.codecrew.youtube.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.codecrew.jpa.AggregateRoot;
import site.codecrew.youtube.domain.YoutubeChannelEvent.YoutubeChannelCreatedEvent;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class YoutubeChannel extends AggregateRoot {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "channel_id", nullable = false, unique = true)
    private String channelId;

    @Embedded
    private YoutubeSyncCursor syncCursor;
    private String uploadPlaylistId;
    private String title;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    private String customUrl;
    private String thumbnail;
    private String lang;
    private Instant publishedAt;

    public static YoutubeChannel of(
        String channelId,
        String uploadPlaylistId,
        String title,
        String description,
        String customUrl,
        String thumbnail,
        String lang,
        Instant publishedAt
    ) {
        YoutubeChannel youtubeChannel = new YoutubeChannel();
        youtubeChannel.channelId = channelId;
        youtubeChannel.uploadPlaylistId = uploadPlaylistId;
        youtubeChannel.title = title;
        youtubeChannel.description = description;
        youtubeChannel.customUrl = customUrl;
        youtubeChannel.thumbnail = thumbnail;
        youtubeChannel.lang = lang;
        youtubeChannel.publishedAt = publishedAt;
        youtubeChannel.syncCursor = YoutubeSyncCursor.ofInitial();
        youtubeChannel.registerEvent(new YoutubeChannelCreatedEvent(youtubeChannel));
        return youtubeChannel;
    }

    public boolean shouldSync(Instant videoPublishedAt) {
        Instant cursor = (syncCursor == null || syncCursor.getLastSyncedAt() == null)
            ? Instant.EPOCH
            : syncCursor.getLastSyncedAt();

        return videoPublishedAt.isAfter(cursor);
    }

    public void updatePublishedAt(Instant publishedAt) {
        this.syncCursor = YoutubeSyncCursor.of(publishedAt, Instant.now());
    }

    public Instant lastSeenVideoPublishedAtOrEpoch() {
        Instant v = (syncCursor == null) ? null : syncCursor.getLastSyncedAt();
        return v == null ? Instant.EPOCH : v;
    }
}
