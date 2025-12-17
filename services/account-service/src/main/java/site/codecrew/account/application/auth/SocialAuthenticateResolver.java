package site.codecrew.account.application.auth;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.codecrew.account.domain.SocialProfile;
import site.codecrew.account.domain.SocialType;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;

@RequiredArgsConstructor
@Component
public class SocialAuthenticateResolver {

    private final List<SocialAuthenticateProvider> providers;

    public SocialProfile loadSocialProfile(SocialCredential socialCredential) {
        SocialAuthenticateProvider loginProvider = getProvider(socialCredential.type());
        return loginProvider.loadSocialProfile(socialCredential);
    }

    private SocialAuthenticateProvider getProvider(SocialType type) {
        for (SocialAuthenticateProvider SocialAuthenticateProvider : providers) {
            if (SocialAuthenticateProvider.supports(type)) {
                return SocialAuthenticateProvider;
            }
        }
        throw new CoreException(CoreErrorCode.INVALID_REQUEST, "Unsupported SNS type: " + type);
    }
}
