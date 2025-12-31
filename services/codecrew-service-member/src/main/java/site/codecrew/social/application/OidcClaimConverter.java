package site.codecrew.social.application;

import java.util.Map;

public interface OidcClaimConverter {

    Map<String, Object> convertClaims(SocialProfile socialProfile);
}
