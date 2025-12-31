package site.codecrew.social.infrastructure;

import static java.util.Objects.isNull;
import static site.codecrew.social.domain.Provider.naver;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;
import site.codecrew.core.util.BearerTokenUtils;
import site.codecrew.social.application.SocialAuthenticateProvider;
import site.codecrew.social.application.SocialProfile;
import site.codecrew.social.config.OAuthNaverProperties;
import site.codecrew.social.domain.Provider;

@RequiredArgsConstructor
@Component
class SocialAuthenticateNaverProvider implements SocialAuthenticateProvider {

    private final OAuthNaverProperties properties;
    private final RestClient client;

    @Override
    public boolean supports(Provider provider) {
        return provider == naver;
    }

    @Override
    public SocialProfile loadSocialProfile(String accessToken) {
        NaverUserResponse response = client.post()
            .uri(properties.getEndpoints().getUserInfo())
            .header("Authorization", "Bearer " + BearerTokenUtils.stripBearerPrefix(accessToken))
            .header("X-Naver-Client-Id", properties.getClientId())
            .header("X-Naver-Client-Secret", properties.getClientSecret())
            .retrieve()
            .body(NaverUserResponse.class);

        if(isNull(response) || isNull(response.response)) {
            throw new CoreException(CoreErrorCode.INTERNAL_ERROR, "Failed to retrieve user profile from Kakao");
        }

        return new SocialProfile(
            String.valueOf(response.response.id),
            response.response.email,
            response.response.name
        );
    }
//    private final OAuthNaverProperties properties;
//    private final RestClient client;

    //    private Map<String, Object> userinfo(String authorization) {
//        if (authorization == null || !authorization.startsWith("Bearer ")) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing bearer token");
//        }
//
//        String accessToken = authorization.substring("Bearer ".length());
//
//        // 네이버 userinfo 호출
//        Map<?, ?> naver = client.post()
//            .uri(properties.getEndpoints().getUserInfo()) // https://openapi.naver.com/v1/nid/me
//            .header("Authorization", "Bearer " + accessToken)
//            .header("X-Naver-Client-Id", properties.getClientId())
//            .header("X-Naver-Client-Secret", properties.getClientSecret())
//            .retrieve()
//            .body(Map.class);
//
//        // response 평탄화
//        Map<?, ?> response = (Map<?, ?>) naver.get("response");
//        String id = String.valueOf(response.get("id"));
//        String email = (String) response.get("email");
//        String name = (String) response.get("name");
//
//        // Keycloak이 잘 읽는 OIDC 스타일 클레임으로 반환
//        return Map.of(
//            "sub", id,
//            "preferred_username", (email != null ? email : id),
//            "email", email,
//            "name", name
//        );
//    }

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
