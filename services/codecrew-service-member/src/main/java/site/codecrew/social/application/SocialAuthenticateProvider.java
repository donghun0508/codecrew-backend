package site.codecrew.social.application;

import site.codecrew.social.domain.Provider;

public interface SocialAuthenticateProvider {

    boolean supports(Provider provider);

    SocialProfile loadSocialProfile(String accessToken);
}
