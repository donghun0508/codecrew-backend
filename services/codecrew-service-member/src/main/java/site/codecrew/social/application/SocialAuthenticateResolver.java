package site.codecrew.social.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;
import site.codecrew.social.domain.Provider;

@RequiredArgsConstructor
@Component
public class SocialAuthenticateResolver {

    private final List<SocialAuthenticateProvider> providers;

    public SocialProfile loadUserInfo(SocialCredential socialCredential) {
        SocialAuthenticateProvider loginProvider = getProvider(socialCredential.provider());
        return loginProvider.loadSocialProfile(socialCredential.accessToken());
    }

    private SocialAuthenticateProvider getProvider(Provider provider) {
        for (SocialAuthenticateProvider SocialAuthenticateProvider : providers) {
            if (SocialAuthenticateProvider.supports(provider)) {
                return SocialAuthenticateProvider;
            }
        }
        throw new CoreException(CoreErrorCode.INVALID_REQUEST, "Unsupported SNS type: ", provider);
    }
}
