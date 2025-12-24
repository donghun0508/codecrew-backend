package site.codecrew.youtube.application;

import java.time.Instant;
import java.util.Iterator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.codecrew.youtube.domain.YoutubeChannel;
import site.codecrew.youtube.domain.YoutubeChannelService;
import site.codecrew.youtube.domain.YoutubeVideoBatch;
import site.codecrew.youtube.domain.YoutubeVideoService;

@RequiredArgsConstructor
@Component
public class YoutubeVideoSyncUseCase {

    private final YoutubeChannelVideoSearcher youtubeChannelVideoSearcher;
    private final YoutubeVideoService youtubeVideoService;
    private final YoutubeChannelService youtubeChannelService;

    public void sync(String channelId) {
        YoutubeChannel channel = youtubeChannelService.findByChannelId(channelId);

        Instant baselineCursor = channel.lastSeenVideoPublishedAtOrEpoch();
        Iterator<YoutubeVideoBatch> iterator = youtubeChannelVideoSearcher.iterateBatches(channel).iterator();

        Instant maxSeen = null;

        while (iterator.hasNext()) {
            YoutubeVideoBatch batch = iterator.next();

            batch.filter(baselineCursor);
            if (batch.isEndFor()) break;

            youtubeVideoService.saveAll(batch.videos());

            Instant batchMax = batch.maxPublishedAt();
            if (batchMax != null && (maxSeen == null || batchMax.isAfter(maxSeen))) {
                maxSeen = batchMax;
            }
        }

        if (maxSeen != null) {
            channel.updatePublishedAt(maxSeen);
            youtubeChannelService.save(channel);
        }
    }
}
