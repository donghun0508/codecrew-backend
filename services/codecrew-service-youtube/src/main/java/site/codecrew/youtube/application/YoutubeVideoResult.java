package site.codecrew.youtube.application;

import java.util.List;
import site.codecrew.youtube.domain.YoutubeVideo;

public record YoutubeVideoResult(
    boolean hasNext,
    List<YoutubeVideoItem> videos,
    Long lastVideoId
) {

    public static YoutubeVideoResult of(List<YoutubeVideo> page, boolean hasNext,
        Long lastVideoId) {
        List<YoutubeVideoItem> items = page.stream()
            .map(v -> new YoutubeVideoItem(
                v.getId(),
                v.getVideoId(),
                v.getTitle(),
                v.getDescription(),
                v.getThumbnailUrl()
            ))
            .toList();

        return new YoutubeVideoResult(hasNext, items, lastVideoId);
    }

    public static YoutubeVideoResult empty() {
        return new YoutubeVideoResult(false, List.of(), null);
    }

    public record YoutubeVideoItem(
        Long id,
        String videoUrl,
        String title,
        String description,
        String thumbnailUrl
    ) {

    }
}
