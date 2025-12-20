package site.codecrew.account.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JsonWebTokenProperties {

    private TokenExpSeconds tokenExpSeconds;
    private TokenClaims tokenClaims;
    private String secret;

    @Getter
    @Setter
    public static class TokenExpSeconds {

        private long access;
        private long refresh;
        private long temporary;
    }

    @Getter
    @Setter
    public static class TokenClaims {

        private String issuer;
        private String audience;
    }
}