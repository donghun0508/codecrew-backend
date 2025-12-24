package site.codecrew.youtube.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.codecrew.youtube.application.YoutubeChannelSearcher;
import site.codecrew.youtube.domain.YoutubeChannel;

@RequiredArgsConstructor
@Component
class YoutubeChannelSearcherImpl implements YoutubeChannelSearcher {

    private final YoutubeClient youtubeClient;
    private final YoutubeWhiteChannelResponseAdapter youtubeWhiteChannelResponseAdapter;

    @Override
    public YoutubeChannel getChannel(String forHandle) {
        YoutubeChannelResponse channelResponse = youtubeClient.getChannel(
            "snippet,contentDetails",
            forHandle
        );
        return youtubeWhiteChannelResponseAdapter.create(channelResponse);
    }
}
