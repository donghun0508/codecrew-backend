package site.codecrew.account.adapter.api.v1.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import site.codecrew.account.application.auth.SocialCredential;
import site.codecrew.account.domain.SocialType;

public record SocialLoginRequest(
    @NotNull SocialType socialType,
    @NotBlank String authorizationCode,
    String nonce,
    String state
) {

    public SocialCredential toSocialCredential() {
        return new SocialCredential(socialType, authorizationCode, nonce, state);
    }

}
