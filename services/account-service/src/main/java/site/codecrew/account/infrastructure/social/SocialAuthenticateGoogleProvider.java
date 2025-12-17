package site.codecrew.account.infrastructure.social;

import static java.util.Objects.isNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import java.util.Collections;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import site.codecrew.account.application.auth.SocialCredential;
import site.codecrew.account.config.OAuthGoogleProperties;
import site.codecrew.account.domain.SocialProfile;
import site.codecrew.account.domain.SocialType;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;

@Component
class SocialAuthenticateGoogleProvider extends AbstractSocialAuthenticateProvider {

    private final OAuthGoogleProperties properties;
    private final RestClient client;
    private final GoogleIdTokenValidator googleIdTokenValidator;

    public SocialAuthenticateGoogleProvider(OAuthGoogleProperties properties, RestClient client) {
        this.properties = properties;
        this.client = client;
        this.googleIdTokenValidator = new GoogleIdTokenValidator(properties.getClientId());
    }

    @Override
    public boolean supports(SocialType type) {
        return type == SocialType.GOOGLE;
    }

    @Override
    protected OAuthTokenSet getOAuthTokenSet(SocialCredential socialCredential) {
        OAuthGoogleProperties.Endpoints ep = properties.getEndpoints();

        GoogleTokenResponse response = client.post()
            .uri(ep.getToken())
            .body(Map.of(
                "code",  socialCredential.authorizationCode(),
                "client_id", properties.getClientId(),
                "client_secret", properties.getClientSecret(),
                "redirect_uri", properties.getRedirectUri(),
                "grant_type", "authorization_code"
            ))
            .retrieve()
            .body(GoogleTokenResponse.class);

        if(isNull(response)) {
            throw new CoreException(CoreErrorCode.INTERNAL_ERROR, "Failed to retrieve access token from Google.");
        }

        return new OAuthTokenSet(response.accessToken, response.idToken, true);
    }

    @Override
    protected SocialProfile parseIdToken(String idToken) {
        return googleIdTokenValidator.authenticateUser(idToken).toMemberProfile();
    }

    @Override
    protected SocialProfile getSocialProfile(String accessToken) {
        OAuthGoogleProperties.Endpoints ep = properties.getEndpoints();

        GoogleUserInfo googleUserInfo = client.get()
            .uri(ep.getUserInfo())
            .header("Authorization", "Bearer " + accessToken)
            .retrieve()
            .body(GoogleUserInfo.class);

        if(isNull(googleUserInfo)) {
            throw new CoreException(CoreErrorCode.INTERNAL_ERROR, "Failed to retrieve user info from Google.");
        }
        return googleUserInfo.toMemberProfile();
    }

    private static class GoogleIdTokenValidator {

        private final String clientId;

        public GoogleIdTokenValidator(String clientId) {
            this.clientId = clientId;
        }

        public GoogleUserInfo authenticateUser(String idToken) {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(clientId))
                .build();

            try {
                GoogleIdToken googleIdToken = verifier.verify(idToken);
                Payload payload = googleIdToken.getPayload();
                return new GoogleUserInfo(payload.getSubject(), payload.getEmail(), (String) payload.get("name"), (String) payload.get("picture"));
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid ID token.", e);
            }
        }
    }

    record GoogleTokenResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("expires_in") Long expiresIn,
        @JsonProperty("scope") String scope,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("id_token") String idToken
    ) {
    }

    record GoogleUserInfo(String sub, String email, String name, String picture) {
        public SocialProfile toMemberProfile() {
            return new SocialProfile(SocialType.GOOGLE, sub, email, name, picture);
        }
    }
}
