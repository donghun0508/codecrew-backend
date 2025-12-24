package site.codecrew.youtube.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class YoutubeVideoBatch {

    private final YoutubeChannel youtubeChannel;
    private final List<YoutubeVideo> rawVideos;

    private List<YoutubeVideo> videos = List.of();
    private boolean endFor = false;
    private Instant maxPublishedAt = null;

    public YoutubeVideoBatch(YoutubeChannel youtubeChannel, List<YoutubeVideo> rawVideos) {
        this.youtubeChannel = youtubeChannel;
        this.rawVideos = rawVideos;
    }

    public void filter(Instant baselineCursor) {
        this.endFor = false;
        this.maxPublishedAt = null;
        this.videos = List.of();

        List<YoutubeVideo> out = new ArrayList<>();

        for (YoutubeVideo v : rawVideos) {
            Instant publishedAt = v.getPublishedAt();
            if (publishedAt == null) {
                continue;
            }

            if (!publishedAt.isAfter(baselineCursor)) {
                this.endFor = true;
                break;
            }

            out.add(v);

            if (this.maxPublishedAt == null || publishedAt.isAfter(this.maxPublishedAt)) {
                this.maxPublishedAt = publishedAt;
            }
        }

        this.videos = out;

        if (out.isEmpty()) {
            this.endFor = true;
        }
    }

    public boolean isEndFor() {
        return endFor;
    }

    public List<YoutubeVideo> videos() {
        return videos;
    }

    public Instant maxPublishedAt() {
        return maxPublishedAt;
    }

    public YoutubeChannel youtubeChannel() {
        return youtubeChannel;
    }
}
