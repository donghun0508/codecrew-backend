package site.codecrew.social.application;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OidcUserInfoQueryUseCase {

    private final SocialAuthenticateResolver socialAuthenticateResolver;
    private final OidcClaimConverter oidcClaimConverter;

    public Map<String, Object> getUserInfo(SocialCredential socialCredential) {
        SocialProfile socialProfile = socialAuthenticateResolver.loadUserInfo(socialCredential);
        return oidcClaimConverter.convertClaims(socialProfile);
    }
}
