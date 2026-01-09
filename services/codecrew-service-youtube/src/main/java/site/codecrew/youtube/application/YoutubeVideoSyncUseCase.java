package site.codecrew.youtube.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import site.codecrew.youtube.domain.YoutubeChannel;
import site.codecrew.youtube.domain.YoutubeChannelService;

@Slf4j
@RequiredArgsConstructor
@Component
public class YoutubeVideoSyncUseCase {

    private final YoutubeChannelService youtubeChannelService;
    private final YoutubeChannelVideoSyncer youtubeChannelVideoSyncer;

    public void sync(String channelId) {
        YoutubeChannel channel = youtubeChannelService.findByChannelId(channelId);

        if (youtubeChannelVideoSyncer.execute(channel)) {
            youtubeChannelService.save(channel);
        }
    }

    public void syncAll() {
        List<YoutubeChannel> channels = youtubeChannelService.findAll();

        for (YoutubeChannel channel : channels) {
            try {
                boolean isUpdated = youtubeChannelVideoSyncer.execute(channel);

                if (isUpdated) {
                    youtubeChannelService.save(channel);
                }
            } catch (Exception e) {
                log.error("동기화 실패: channelId={}", channel.getChannelId(), e);
            }
        }
    }
}
