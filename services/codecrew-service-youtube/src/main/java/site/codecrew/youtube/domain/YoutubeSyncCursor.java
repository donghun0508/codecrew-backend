package site.codecrew.youtube.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
class YoutubeSyncCursor {

    @Column(name = "last_seen_video_published_at")
    private Instant lastSeenVideoPublishedAt;

    @Column(name = "last_synced_at")
    private Instant lastSyncedAt;

    public static YoutubeSyncCursor ofInitial() {
        return new YoutubeSyncCursor(Instant.EPOCH, null);
    }

    public static YoutubeSyncCursor of(
        Instant lastSeenVideoPublishedAt,
        Instant lastSyncedAt
    ) {
        return new YoutubeSyncCursor(lastSeenVideoPublishedAt, lastSyncedAt);
    }


    public Instant getLastSyncedAt() {
        return lastSyncedAt;
    }
}






