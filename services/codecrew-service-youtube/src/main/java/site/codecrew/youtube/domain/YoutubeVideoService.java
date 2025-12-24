package site.codecrew.youtube.domain;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class YoutubeVideoService {

    private final YoutubeVideoRepository youtubeVideoRepository;

    @Transactional
    public void saveAll(List<YoutubeVideo> youtubeVideos) {
        youtubeVideoRepository.saveAll(youtubeVideos);
    }
}
