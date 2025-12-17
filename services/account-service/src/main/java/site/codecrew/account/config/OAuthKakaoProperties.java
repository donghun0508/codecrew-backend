package site.codecrew.account.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("oauth.kakao")
public class OAuthKakaoProperties {
    private String clientId;
    private String redirectUri;
    private Endpoints endpoints = new Endpoints();

    @Getter
    @Setter
    public static class Endpoints {
        private String token;
        private String userInfo;
    }
}
