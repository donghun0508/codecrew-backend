package site.codecrew.youtube.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YoutubeChannelRepository extends JpaRepository<YoutubeChannel, Long> {

    boolean existsByCustomUrl(String customUrl);

    Optional<YoutubeChannel> findByChannelId(String channelId);
}
