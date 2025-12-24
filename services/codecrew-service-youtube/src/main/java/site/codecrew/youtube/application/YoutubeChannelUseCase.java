package site.codecrew.youtube.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import site.codecrew.youtube.domain.YoutubeChannel;
import site.codecrew.youtube.domain.YoutubeChannelService;

@Slf4j
@RequiredArgsConstructor
@Component
public class YoutubeChannelUseCase {

    private final YoutubeChannelSearcher youtubeChannelSearcher;
    private final YoutubeChannelService youtubeChannelService;

    public void register(String forHandle) {
        if(youtubeChannelService.existsByForHandle(forHandle)) {
            log.info("이미 등록된 채널입니다. forHandle={}", forHandle);
            return;
        }
        YoutubeChannel youtubeChannel = youtubeChannelSearcher.getChannel(forHandle);
        youtubeChannelService.save(youtubeChannel);
    }
}
