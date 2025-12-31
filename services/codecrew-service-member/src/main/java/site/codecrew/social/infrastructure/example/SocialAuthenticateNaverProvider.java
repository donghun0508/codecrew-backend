/*
package site.codecrew.social.infrastructure.example;


import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.isNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URLEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import site.codecrew.account.application.auth.SocialCredential;
import site.codecrew.account.config.OAuthNaverProperties;
import site.codecrew.account.domain.SocialProfile;
import site.codecrew.account.domain.SocialType;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;

@RequiredArgsConstructor
@Component
class SocialAuthenticateNaverProvider extends AbstractSocialAuthenticateProvider {

    private final OAuthNaverProperties properties;
    private final RestClient client;

    @Override
    public boolean supports(SocialType type) {
        return type == SocialType.NAVER;
    }

    @Override
    protected OAuthTokenSet getOAuthTokenSet(SocialCredential socialCredential) {
        NaverTokenResponse response = client.post()
            .uri(properties.getEndpoints().getToken())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(
                "grant_type=authorization_code" +
                    "&client_id=" + properties.getClientId() +
                    "&client_secret=" + properties.getClientSecret() +
                    "&redirect_uri=" + URLEncoder.encode(properties.getRedirectUri(), UTF_8) +
                    "&code=" + socialCredential.authorizationCode() +
                    "&state=" + socialCredential.state()
            )
            .retrieve()
            .body(NaverTokenResponse.class);

        if (isNull(response)) {
            throw new CoreException(CoreErrorCode.INTERNAL_ERROR, "Failed to retrieve access token from Google.");
        }

        return new OAuthTokenSet(
            response.accessToken,
            null,
            false
        );
    }

    @Override
    protected SocialProfile parseIdToken(String idToken) {
        return null;
    }

    @Override
    protected SocialProfile getSocialProfile(String accessToken) {
        NaverUserResponse response = client.post()
            .uri(properties.getEndpoints().getUserInfo())
            .header("Authorization", "Bearer " + accessToken)
            .header("X-Naver-Client-Id", properties.getClientId())
            .header("X-Naver-Client-Secret", properties.getClientSecret())
            .retrieve()
            .body(NaverUserResponse.class);


        if(isNull(response) || isNull(response.response)) {
            throw new CoreException(CoreErrorCode.INTERNAL_ERROR, "Failed to retrieve user profile from Kakao.");
        }

        return new SocialProfile(
            SocialType.NAVER,
            String.valueOf(response.response.id),
            response.response.email,
            response.response.name,
            (response.response.profileImage == null) ? null : response.response.profileImage
        );
    }

    record NaverTokenResponse(

        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("refresh_token")
        String refreshToken,

        @JsonProperty("token_type")
        String tokenType,

        @JsonProperty("expires_in")
        Integer expiresIn,

        @JsonProperty("error")
        String error,

        @JsonProperty("error_description")
        String errorDescription
    ) {}

    record NaverUserResponse(
        @JsonProperty("resultcode")
        String resultCode,

        @JsonProperty("message")
        String message,

        @JsonProperty("response")
        NaverUserInfo response
    ) {
        record NaverUserInfo(

            @JsonProperty("email")
            String email,

            @JsonProperty("nickname")
            String nickname,

            @JsonProperty("profile_image")
            String profileImage,

            @JsonProperty("age")
            String age,

            @JsonProperty("gender")
            String gender,

            @JsonProperty("id")
            String id,

            @JsonProperty("name")
            String name,

            @JsonProperty("birthday")
            String birthday,

            @JsonProperty("birthyear")
            String birthyear,

            @JsonProperty("mobile")
            String mobile
        ) {}
    }
}
*/