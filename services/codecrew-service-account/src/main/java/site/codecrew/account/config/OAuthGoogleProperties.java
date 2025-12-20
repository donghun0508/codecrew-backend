package site.codecrew.account.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("oauth.google")
public class OAuthGoogleProperties {

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private Endpoints endpoints = new Endpoints();

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getClientSecret() { return clientSecret; }
    public void setClientSecret(String clientSecret) { this.clientSecret = clientSecret; }

    public String getRedirectUri() { return redirectUri; }
    public void setRedirectUri(String redirectUri) { this.redirectUri = redirectUri; }

    public Endpoints getEndpoints() { return endpoints; }
    public void setEndpoints(Endpoints endpoints) { this.endpoints = endpoints; }

    public static class Endpoints {
        private String authorization;
        private String token;
        private String userInfo;

        public String getAuthorization() { return authorization; }
        public void setAuthorization(String authorization) { this.authorization = authorization; }

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }

        public String getUserInfo() { return userInfo; }
        public void setUserInfo(String userInfo) { this.userInfo = userInfo; }
    }
}
