package site.codecrew.youtube.infrastructure;

import java.time.Instant;
import org.springframework.stereotype.Component;
import site.codecrew.youtube.domain.YoutubeChannel;
import site.codecrew.youtube.infrastructure.YoutubeChannelResponse.Item;

@Component
public class YoutubeWhiteChannelResponseAdapter {

    public YoutubeChannel create(YoutubeChannelResponse response) {
        Item item = response.items().getFirst();

        return YoutubeChannel.of(
            item.id(),
            item.contentDetails().relatedPlaylists().uploads(),
            item.snippet().title(),
            item.snippet().description(),
            item.snippet().customUrl(),
            item.snippet().thumbnails().high().url(),
            item.snippet().country(),
            Instant.parse(item.snippet().publishedAt())
        );
    }
}
