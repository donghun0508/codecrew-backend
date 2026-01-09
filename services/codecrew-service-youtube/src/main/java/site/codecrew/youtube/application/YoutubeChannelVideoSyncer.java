package site.codecrew.youtube.application;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import site.codecrew.youtube.domain.YoutubeChannel;
import site.codecrew.youtube.domain.YoutubeVideoBatch;
import site.codecrew.youtube.domain.YoutubeVideoService;

@Slf4j
@RequiredArgsConstructor
@Component
public class YoutubeChannelVideoSyncer {

    private final YoutubeChannelVideoSearcher searcher;
    private final YoutubeVideoService videoService;

    public boolean execute(YoutubeChannel channel) {
        Instant baseline = channel.lastSeenVideoPublishedAtOrEpoch();
        var batchIterator = searcher.iterateBatches(channel).iterator();

        Instant maxSeen = null;

        while (batchIterator.hasNext()) {
            YoutubeVideoBatch batch = batchIterator.next();

            batch.filter(baseline);
            if (batch.isEndFor()) {
                break;
            }

            videoService.saveAll(batch.videos());

            maxSeen = resolveMaxTime(maxSeen, batch.maxPublishedAt());
        }

        if (maxSeen != null) {
            channel.updatePublishedAt(maxSeen);
            return true;
        }

        return false;
    }

    private Instant resolveMaxTime(Instant currentMax, Instant batchMax) {
        if (batchMax == null) {
            return currentMax;
        }
        return (currentMax == null || batchMax.isAfter(currentMax)) ? batchMax : currentMax;
    }
}