package site.codecrew.social.infrastructure;

import java.util.Map;
import org.springframework.stereotype.Component;
import site.codecrew.social.application.OidcClaimConverter;
import site.codecrew.social.application.SocialProfile;

@Component
class KeycloakClaimConverter implements OidcClaimConverter {

    @Override
    public Map<String, Object> convertClaims(SocialProfile socialProfile) {
        return Map.of(
            KeycloakClaimConstants.SUB, socialProfile.id(),
            KeycloakClaimConstants.PREFERRED_USERNAME, (socialProfile.email() != null ? socialProfile.email() : socialProfile.id()),
            KeycloakClaimConstants.EMAIL, socialProfile.email(),
            KeycloakClaimConstants.NAME, socialProfile.name()
        );
    }

    private static class KeycloakClaimConstants {
        private static final String SUB = "sub";
        private static final String PREFERRED_USERNAME = "preferred_username";
        private static final String EMAIL = "email";
        private static final String NAME = "name";
    }
}
