package site.codecrew.account.application.auth;


import site.codecrew.account.domain.SocialProfile;
import site.codecrew.account.domain.SocialType;

public interface SocialAuthenticateProvider {

    boolean supports(SocialType SocialType);

    SocialProfile loadSocialProfile(SocialCredential socialCredential);
}
