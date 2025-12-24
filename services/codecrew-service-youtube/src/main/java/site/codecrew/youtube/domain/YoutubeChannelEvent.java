package site.codecrew.youtube.domain;

import lombok.Getter;
import site.codecrew.core.domain.DomainEvent;

public class YoutubeChannelEvent {

    @Getter
    public static class YoutubeChannelCreatedEvent extends DomainEvent {

        private final String channelId;
        private final String uploadPlayListId;

        protected YoutubeChannelCreatedEvent(YoutubeChannel youtubeChannel) {
            super(YoutubeChannel.class.getTypeName(), youtubeChannel.getChannelId(), 1);
            this.channelId = youtubeChannel.getChannelId();
            this.uploadPlayListId = youtubeChannel.getUploadPlaylistId();
        }
    }

}
