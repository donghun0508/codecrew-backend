package site.codecrew.account.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("oauth.naver")
public class OAuthNaverProperties {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private Endpoints endpoints = new Endpoints();

    @Getter
    @Setter
    public static class Endpoints {
        private String token;
        private String userInfo;
    }
}
