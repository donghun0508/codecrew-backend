package site.codecrew.social.adapter.api.v1;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import site.codecrew.social.application.SocialCredential;
import site.codecrew.social.application.OidcUserInfoQueryUseCase;
import site.codecrew.social.domain.Provider;

@RestController
@RequiredArgsConstructor
public class OidcUserInfoProxyController implements OidcUserInfoProxyV1ApiSpec {

    private final OidcUserInfoQueryUseCase oidcUserInfoQueryUseCase;

    @Override
    public Map<String, Object> getUserInfo(Provider provider, String authorization) {
        String accessToken = getAccessToken(authorization);
        return oidcUserInfoQueryUseCase.getUserInfo(new SocialCredential(provider, accessToken));
    }

    private String getAccessToken(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing bearer token");
        }

        return authorization.substring("Bearer ".length());
    }
}
