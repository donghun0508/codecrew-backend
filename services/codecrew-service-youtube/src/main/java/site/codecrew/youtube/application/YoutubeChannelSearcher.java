package site.codecrew.youtube.application;

import site.codecrew.youtube.domain.YoutubeChannel;

public interface YoutubeChannelSearcher {

    YoutubeChannel getChannel(String channelName);
}
