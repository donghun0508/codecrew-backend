package site.codecrew.youtube.domain;

import lombok.Getter;
import lombok.ToString;
import site.codecrew.core.domain.DomainEvent;

public class YoutubeChannelEvent {

    @Getter
    @ToString
    public static class YoutubeChannelCreatedEvent extends DomainEvent {

        private final String channelId;
        private final String uploadPlayListId;

        protected YoutubeChannelCreatedEvent(YoutubeChannel youtubeChannel) {
            super(YoutubeChannel.class.getTypeName(), youtubeChannel.getChannelId());
            this.channelId = youtubeChannel.getChannelId();
            this.uploadPlayListId = youtubeChannel.getUploadPlaylistId();
        }
    }

}
