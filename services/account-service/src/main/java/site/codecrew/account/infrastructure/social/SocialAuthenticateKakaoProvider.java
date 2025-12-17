package site.codecrew.account.infrastructure.social;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.isNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URLEncoder;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import site.codecrew.account.application.auth.SocialCredential;
import site.codecrew.account.config.OAuthKakaoProperties;
import site.codecrew.account.domain.SocialProfile;
import site.codecrew.account.domain.SocialType;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;

@RequiredArgsConstructor
@Component
class SocialAuthenticateKakaoProvider extends AbstractSocialAuthenticateProvider {

    private final OAuthKakaoProperties properties;
    private final RestClient client;

    @Override
    public boolean supports(SocialType type) {
        return type == SocialType.KAKAO;
    }

    @Override
    protected OAuthTokenSet getOAuthTokenSet(SocialCredential socialCredential) {
        KakaoTokenResponse response = client.post()
            .uri(properties.getEndpoints().getToken())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(
                "grant_type=authorization_code" +
                    "&client_id=" + properties.getClientId() +
                    "&redirect_uri=" + URLEncoder.encode(properties.getRedirectUri(), UTF_8) +
                    "&code=" + socialCredential.authorizationCode()
            )
            .retrieve()
            .body(KakaoTokenResponse.class);


        if (isNull(response)) {
            throw new CoreException(CoreErrorCode.INTERNAL_ERROR,
                "Failed to retrieve access token from Google.");
        }

        return new OAuthTokenSet(
            response.accessToken,
            response.idToken,
            false
        );
    }

    @Override
    protected SocialProfile parseIdToken(String idToken) {
        return null;
    }

    @Override
    protected SocialProfile getSocialProfile(String accessToken) {
        KakaoUserResponse response = client.post()
            .uri(properties.getEndpoints().getUserInfo())
            .header("Authorization", "Bearer " + accessToken)
            .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
            .retrieve()
            .body(KakaoUserResponse.class);

        if(isNull(response) || isNull(response.kakaoAccount())) {
            throw new CoreException(CoreErrorCode.INTERNAL_ERROR, "Failed to retrieve user profile from Kakao.");
        }

        return new SocialProfile(
            SocialType.KAKAO,
            String.valueOf(response.id),
            response.kakaoAccount.email,
            response.kakaoAccount.name,
            (response.kakaoAccount.profile == null) ? null : response.kakaoAccount.profile.profileImageUrl
        );
    }

    record KakaoTokenResponse(

        @JsonProperty("token_type")
        String tokenType,

        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("id_token")
        String idToken,

        @JsonProperty("expires_in")
        Long expiresIn,

        @JsonProperty("refresh_token")
        String refreshToken,

        @JsonProperty("refresh_token_expires_in")
        Long refreshTokenExpiresIn,

        @JsonProperty("scope")
        String scope
    ) {

    }

    record KakaoUserResponse(

        @JsonProperty("id")
        Long id,

        @JsonProperty("connected_at")
        String connectedAt,

        @JsonProperty("kakao_account")
        KakaoAccount kakaoAccount,

        @JsonProperty("properties")
        KakaoProperties properties,

        @JsonProperty("for_partner")
        KakaoForPartner forPartner
    ) {

        record KakaoAccount(

            @JsonProperty("profile_nickname_needs_agreement")
            Boolean profileNicknameNeedsAgreement,

            @JsonProperty("profile_image_needs_agreement")
            Boolean profileImageNeedsAgreement,

            @JsonProperty("profile")
            KakaoProfile profile,

            @JsonProperty("name_needs_agreement")
            Boolean nameNeedsAgreement,

            @JsonProperty("name")
            String name,

            @JsonProperty("email_needs_agreement")
            Boolean emailNeedsAgreement,

            @JsonProperty("is_email_valid")
            Boolean isEmailValid,

            @JsonProperty("is_email_verified")
            Boolean isEmailVerified,

            @JsonProperty("email")
            String email,

            @JsonProperty("age_range_needs_agreement")
            Boolean ageRangeNeedsAgreement,

            @JsonProperty("age_range")
            String ageRange,

            @JsonProperty("birthyear_needs_agreement")
            Boolean birthyearNeedsAgreement,

            @JsonProperty("birthyear")
            String birthyear,

            @JsonProperty("birthday_needs_agreement")
            Boolean birthdayNeedsAgreement,

            @JsonProperty("birthday")
            String birthday,

            @JsonProperty("birthday_type")
            String birthdayType,

            @JsonProperty("is_leap_month")
            Boolean isLeapMonth,

            @JsonProperty("gender_needs_agreement")
            Boolean genderNeedsAgreement,

            @JsonProperty("gender")
            String gender,

            @JsonProperty("phone_number_needs_agreement")
            Boolean phoneNumberNeedsAgreement,

            @JsonProperty("phone_number")
            String phoneNumber,

            @JsonProperty("ci_needs_agreement")
            Boolean ciNeedsAgreement,

            @JsonProperty("ci")
            String ci,

            @JsonProperty("ci_authenticated_at")
            String ciAuthenticatedAt
        ) {

            record KakaoProfile(

                @JsonProperty("nickname")
                String nickname,

                @JsonProperty("thumbnail_image_url")
                String thumbnailImageUrl,

                @JsonProperty("profile_image_url")
                String profileImageUrl,

                @JsonProperty("is_default_image")
                Boolean isDefaultImage,

                @JsonProperty("is_default_nickname")
                Boolean isDefaultNickname
            ) {

            }
        }

        record KakaoProperties(
            @JsonProperty("${CUSTOM_PROPERTY_KEY}")
            Map<String, Object> customProperties
        ) {

        }

        record KakaoForPartner(

            @JsonProperty("uuid")
            String uuid
        ) {

        }

    }
}
