package site.codecrew.youtube.adapter.api.v1;

import java.util.List;
import site.codecrew.youtube.application.YoutubeVideoResult;

public record YoutubeVideoResponse(
    boolean hasNext,
    List<YoutubeVideoItemResponse> videos,
    Long lastVideoId
) {

    public static YoutubeVideoResponse from(YoutubeVideoResult result) {
        return new YoutubeVideoResponse(
            result.hasNext(),
            result.videos().stream()
                .map(item -> new YoutubeVideoItemResponse(
                    item.id(),
                    "https://www.youtube.com/watch?v=" + item.videoUrl(),
                    item.title(),
                    item.description(),
                    item.thumbnailUrl()
                ))
                .toList(),
            result.lastVideoId()
        );
    }

    public record YoutubeVideoItemResponse(
        Long id,
        String videoUrl,
        String title,
        String description,
        String thumbnailUrl
    ) {}
}
