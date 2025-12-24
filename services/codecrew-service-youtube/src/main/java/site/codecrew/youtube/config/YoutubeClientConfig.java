package site.codecrew.youtube.config;

import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import site.codecrew.youtube.infrastructure.YoutubeClient;

@Configuration
public class YoutubeClientConfig {

    @Bean
    YoutubeClient youtubeClient(YouTubeProperties props) {
        RestClient restClient = RestClient.builder()
                .baseUrl(props.getApi().getBaseUrl())
                .defaultUriVariables(Map.of(
                        "regionCode", "kr",
                        "apiKey", props.getApi().getApiKey()
                ))
                .build();

        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build()
                .createClient(YoutubeClient.class);
    }
}