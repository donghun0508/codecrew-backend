package site.codecrew.social.application;

import site.codecrew.social.domain.Provider;

public record SocialCredential(Provider provider, String accessToken) {

}
