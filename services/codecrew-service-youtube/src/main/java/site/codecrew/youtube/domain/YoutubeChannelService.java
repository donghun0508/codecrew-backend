package site.codecrew.youtube.domain;

import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;
import site.codecrew.jpa.DomainEventPublisher;

@RequiredArgsConstructor
@Service
public class YoutubeChannelService {

    private final YoutubeChannelRepository youtubeChannelRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Transactional
    public YoutubeChannel save(YoutubeChannel youtubeChannel) {
        YoutubeChannel saved = youtubeChannelRepository.save(youtubeChannel);
        domainEventPublisher.publishEvent(youtubeChannel.getDomainEvents());
        return saved;
    }

    public boolean existsByForHandle(String forHandle) {
        return youtubeChannelRepository.existsByCustomUrl(forHandle);
    }

    public YoutubeChannel findByChannelId(String channelId) {
        return youtubeChannelRepository.findByChannelId(channelId)
            .orElseThrow(() -> new CoreException(CoreErrorCode.NOT_FOUND,
                "유튜브 채널을 찾을 수 없습니다. channelId=" + channelId));
    }

    public List<YoutubeChannel> findAll() {
        return youtubeChannelRepository.findAll();
    }
}
