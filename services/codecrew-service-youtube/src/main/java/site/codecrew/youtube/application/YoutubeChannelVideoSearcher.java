package site.codecrew.youtube.application;

import site.codecrew.youtube.domain.YoutubeChannel;
import site.codecrew.youtube.domain.YoutubeVideoBatch;

public interface YoutubeChannelVideoSearcher {

    Iterable<YoutubeVideoBatch> iterateBatches(YoutubeChannel channel);
}
