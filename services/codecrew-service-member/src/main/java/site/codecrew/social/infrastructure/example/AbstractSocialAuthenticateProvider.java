/*
package site.codecrew.social.infrastructure.example;

import static java.util.Objects.isNull;

import lombok.extern.slf4j.Slf4j;
import site.codecrew.account.application.auth.SocialAuthenticateProvider;
import site.codecrew.account.application.auth.SocialCredential;
import site.codecrew.account.domain.SocialProfile;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;

@Slf4j
abstract class AbstractSocialAuthenticateProvider implements SocialAuthenticateProvider {

    @Override
    public SocialProfile loadSocialProfile(SocialCredential socialCredential) {
        OAuthTokenSet tokenSet = getOAuthTokenSet(socialCredential);
        if (isNull(tokenSet)) {
            throw new CoreException(CoreErrorCode.INTERNAL_ERROR, "Failed to retrieve access token.");
        }
        if(tokenSet.useIdToken()) {
            try {
                return parseIdToken(tokenSet.idToken());
            } catch (Exception e) {
                log.error("[AbstractSocialLoginProvider#parseIdToken()]: {}", e.getMessage(), e);
            }
        }
        return getSocialProfile(tokenSet.accessToken());
    }

    protected abstract OAuthTokenSet getOAuthTokenSet(SocialCredential socialCredential);
    protected abstract SocialProfile getSocialProfile(String accessToken);
    protected abstract SocialProfile parseIdToken(String idToken);

    public record OAuthTokenSet(String accessToken, String idToken, boolean usedIdToken) {
        public boolean useIdToken() {
            return idToken != null && usedIdToken;
        }
    }
}
*/