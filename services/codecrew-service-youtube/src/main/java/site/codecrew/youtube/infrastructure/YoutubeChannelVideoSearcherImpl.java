package site.codecrew.youtube.infrastructure;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import site.codecrew.youtube.application.YoutubeChannelVideoSearcher;
import site.codecrew.youtube.domain.YoutubeChannel;
import site.codecrew.youtube.domain.YoutubeVideo;
import site.codecrew.youtube.domain.YoutubeVideoBatch;

@Slf4j
@RequiredArgsConstructor
@Component
class YoutubeChannelVideoSearcherImpl implements YoutubeChannelVideoSearcher {

    private static final int MAX_RESULTS = 5; // 테스트용
    private static final String PART = "contentDetails,snippet";

    private final YoutubeClient youtubeClient;

    @Override
    public Iterable<YoutubeVideoBatch> iterateBatches(YoutubeChannel channel) {
        return () -> new Iterator<>() {

            private final String uploadPlaylistId = channel.getUploadPlaylistId();
            private final String channelId = channel.getChannelId();

            private String pageToken = null;

            private boolean finished = false;

            private YoutubeVideoBatch buffered = null;
            private boolean finishAfterConsume = false; // 이번 buffered가 마지막 페이지인지

            @Override
            public boolean hasNext() {
                if (finished) {
                    return false;
                }
                if (buffered != null) {
                    return true;
                }

                FetchResult fetched = fetchOnce();
                if (fetched == null) {
                    finished = true;
                    return false;
                }

                this.buffered = fetched.batch();
                this.finishAfterConsume = fetched.noMorePages();
                return true;
            }

            @Override
            public YoutubeVideoBatch next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                YoutubeVideoBatch current = buffered;
                buffered = null;

                if (finishAfterConsume) {
                    finished = true;
                    finishAfterConsume = false;
                }

                return current;
            }

            private FetchResult fetchOnce() {
                YoutubePlaylistItemsResponse res = youtubeClient.playlistItems(
                    PART,
                    uploadPlaylistId,
                    MAX_RESULTS,
                    pageToken
                );

                int size = (res.items() == null) ? 0 : res.items().size();
                log.info(
                    "Fetched playlist items: channelId={}, playlistId={}, pageToken={}, fetched={}",
                    channelId, uploadPlaylistId, pageToken, size);

                if (size == 0) {
                    return null;
                }

                pageToken = res.nextPageToken();
                boolean noMorePages = (pageToken == null || pageToken.isBlank());

                YoutubeVideoBatch batch = toBatch(channel, channelId, res.items());
                return new FetchResult(batch, noMorePages);
            }

            private YoutubeVideoBatch toBatch(
                YoutubeChannel channel,
                String channelId,
                List<YoutubePlaylistItemsResponse.Item> items
            ) {
                List<YoutubeVideo> rawVideos = new ArrayList<>();

                for (YoutubePlaylistItemsResponse.Item it : items) {
                    if (it == null || it.contentDetails() == null) {
                        continue;
                    }

                    String videoId = it.contentDetails().videoId();
                    String publishedAtRaw = it.contentDetails().videoPublishedAt();
                    if (videoId == null || publishedAtRaw == null) {
                        continue;
                    }

                    Instant publishedAt = Instant.parse(publishedAtRaw);

                    String title = it.snippet() != null ? it.snippet().title() : null;
                    String desc = it.snippet() != null ? it.snippet().description() : null;
                    String thumb = pickThumb(it);

                    rawVideos.add(
                        YoutubeVideo.of(videoId, channelId, title, desc, thumb, publishedAt));
                }

                return new YoutubeVideoBatch(channel, rawVideos);
            }

            private String pickThumb(YoutubePlaylistItemsResponse.Item it) {
                if (it.snippet() == null || it.snippet().thumbnails() == null) {
                    return null;
                }
                var t = it.snippet().thumbnails();
                if (t.high() != null && t.high().url() != null) {
                    return t.high().url();
                }
                if (t.medium() != null && t.medium().url() != null) {
                    return t.medium().url();
                }
                if (t.def() != null && t.def().url() != null) {
                    return t.def().url();
                }
                return null;
            }
        };
    }

    private record FetchResult(YoutubeVideoBatch batch, boolean noMorePages) {}
}
