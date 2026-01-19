package site.codecrew.member.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakAdminProperties {

    private Admin admin;
    private CodeCrew codecrew;

    @Getter
    @Setter
    public static class Admin {
        private String serverUrl;
        private String realm;
        private String clientId;
        private String grantType;
        private String username;
        private String password;
    }

    @Getter
    @Setter
    public static class CodeCrew {
        private String realm;
    }
}