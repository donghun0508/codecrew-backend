package site.codecrew.youtube.application;

import java.util.List;
import site.codecrew.youtube.domain.NextPageToken;
import site.codecrew.youtube.domain.YoutubeChannel;
import site.codecrew.youtube.domain.YoutubeVideo;

public class YoutubeVideoCollection {

    private final YoutubeChannel channel;
    private NextPageToken nextPageToken;
    private List<YoutubeVideo> youtubeVideos;

    public YoutubeVideoCollection(YoutubeChannel channel) {
        this.channel = channel;
    }

    public static YoutubeVideoCollection from(YoutubeChannel channel) {
        YoutubeVideoCollection collection = new YoutubeVideoCollection(channel);
        collection.nextPageToken = NextPageToken.init();
        return collection;
    }

    public boolean isNotEnd() {
        return false;
    }
}





