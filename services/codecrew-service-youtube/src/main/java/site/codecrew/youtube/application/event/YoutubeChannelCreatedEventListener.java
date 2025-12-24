package site.codecrew.youtube.application.event;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import site.codecrew.youtube.application.YoutubeVideoSyncUseCase;
import site.codecrew.youtube.domain.YoutubeChannelEvent.YoutubeChannelCreatedEvent;

@RequiredArgsConstructor
@Component
class YoutubeChannelCreatedEventListener {

    private final YoutubeVideoSyncUseCase youtubeVideoSyncUseCase;

    @TransactionalEventListener
    @Async
    public void onChannelCreated(YoutubeChannelCreatedEvent event) {
        youtubeVideoSyncUseCase.sync(event.getChannelId());
    }

}
