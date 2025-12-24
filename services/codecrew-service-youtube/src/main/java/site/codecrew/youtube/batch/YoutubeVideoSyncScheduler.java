package site.codecrew.youtube.batch;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.codecrew.youtube.application.YoutubeVideoSyncUseCase;
import site.codecrew.youtube.domain.YoutubeChannelService;

@RequiredArgsConstructor
@Component
public class YoutubeVideoSyncScheduler {

    private final YoutubeChannelService youtubeChannelService;
    private final YoutubeVideoSyncUseCase youtubeVideoSyncUseCase;

    public void sync() {
        youtubeChannelService.findAll().forEach(
            channel -> youtubeVideoSyncUseCase.sync(channel.getChannelId())
        );
    }
}
