package site.codecrew.member.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class KeycloakConfig {

    @Bean
    public Keycloak keycloak(KeycloakAdminProperties properties) {
        return KeycloakBuilder.builder()
            .serverUrl(properties.getAdmin().getServerUrl())
            .realm(properties.getAdmin().getRealm())
            .clientId(properties.getAdmin().getClientId())
            .grantType(properties.getAdmin().getGrantType())
            .username(properties.getAdmin().getUsername())
            .password(properties.getAdmin().getPassword())
            .build();
    }

    @Bean
    public UsersResource usersResource(Keycloak keycloak, KeycloakAdminProperties properties) {
        return keycloak.realm(properties.getCodecrew().getRealm()).users();
    }
}