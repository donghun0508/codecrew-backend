package site.codecrew.youtube.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class YoutubeVideoSyncService {

    private final YoutubeChannelRepository youtubeChannelRepository;
    private final YoutubeVideoRepository youtubeVideoRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(YoutubeVideoBatch page) {
        youtubeChannelRepository.save(page.youtubeChannel());
        youtubeVideoRepository.saveAll(page.videos());
    }
}
