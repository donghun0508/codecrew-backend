package site.codecrew.youtube.application.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import site.codecrew.youtube.application.YoutubeVideoSyncUseCase;
import site.codecrew.youtube.domain.YoutubeChannelEvent.YoutubeChannelCreatedEvent;

@Slf4j
@RequiredArgsConstructor
@Component
class YoutubeChannelCreatedEventListener {

    private final YoutubeVideoSyncUseCase youtubeVideoSyncUseCase;

    @TransactionalEventListener
    @Async
    public void onChannelCreated(YoutubeChannelCreatedEvent event) {
        log.info("유튜브 채널 생성 이벤트 수신: YoutubeChannelCreatedEvent={}", event);
        youtubeVideoSyncUseCase.sync(event.getChannelId());
    }

}
