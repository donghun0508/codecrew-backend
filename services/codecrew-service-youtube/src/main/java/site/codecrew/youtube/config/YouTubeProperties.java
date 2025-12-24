package site.codecrew.youtube.config;

import java.time.Duration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "youtube")
public class YouTubeProperties {

    private Api api = new Api();

    @Data
    public static class Api {

        private String baseUrl;
        private String apiKey;

        private Timeout timeout = new Timeout();
        private Retry retry = new Retry();
        private Defaults defaults = new Defaults();
        private Endpoints endpoints = new Endpoints();
        private RateLimit rateLimit = new RateLimit();
        private Logging logging = new Logging();
    }

    @Data
    public static class Timeout {

        private Duration connect;
        private Duration read;
    }

    @Data
    public static class Retry {

        private int maxAttempts;
        private Duration backoff;
    }

    @Data
    public static class Defaults {

        private String part;
        private String regionCode;
        private String hl;
    }

    @Data
    public static class Endpoints {

        private String search;
        private String videos;
        private String channels;
        private String playlists;
        private String playlistItems;
    }

    @Data
    public static class RateLimit {

        private boolean enabled;
        private int requestsPerSecond;
    }

    @Data
    public static class Logging {

        private boolean request;
        private boolean response;
    }
}