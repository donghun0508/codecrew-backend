package site.codecrew.youtube.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import site.codecrew.youtube.domain.YoutubeVideoQueryService;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class YoutubeVideoQueryUseCase {

    private final YoutubeVideoQueryService youtubeVideoQueryService;

    public YoutubeVideoResult readAllInfiniteScroll(Long lastVideoId, Long pageSize) {
        int size = (pageSize == null || pageSize <= 0) ? 20 : Math.toIntExact(pageSize);
        return youtubeVideoQueryService.readAllInfiniteScroll(lastVideoId, (long) size);
    }
}
