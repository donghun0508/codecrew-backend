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

    public YoutubeVideoResult readAllInfiniteScroll(YoutubeVideoQuery query) {
        int size = (query.size() == null || query.size() <= 0) ? 20 : Math.toIntExact(query.size());
        return youtubeVideoQueryService.readAllInfiniteScroll(query.lastVideoId(), (long) size, query.searchQuery());
    }
}
