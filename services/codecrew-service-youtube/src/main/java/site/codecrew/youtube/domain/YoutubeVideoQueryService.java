package site.codecrew.youtube.domain;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.codecrew.youtube.application.YoutubeVideoResult;

@RequiredArgsConstructor
@Service
public class YoutubeVideoQueryService {

    private final YoutubeVideoRepository youtubeVideoRepository;

    @Transactional(readOnly = true)
    public YoutubeVideoResult readAllInfiniteScroll(Long lastVideoId, Long pageSize) {
        int size = (pageSize == null || pageSize <= 0) ? 20 : Math.toIntExact(pageSize);
        int fetchSize = size + 1;

        List<Long> ids = youtubeVideoRepository.findPageIds(lastVideoId, fetchSize);
        if (ids.isEmpty()) return YoutubeVideoResult.empty();

        boolean hasNext = ids.size() > size;
        List<Long> pageIds = hasNext ? ids.subList(0, size) : ids;

        List<YoutubeVideo> videos = youtubeVideoRepository.findAllByIdsOrderByIdDesc(pageIds);
        Long next = videos.isEmpty() ? null : videos.getLast().getId();
        return YoutubeVideoResult.of(videos, hasNext, next);
    }

}
