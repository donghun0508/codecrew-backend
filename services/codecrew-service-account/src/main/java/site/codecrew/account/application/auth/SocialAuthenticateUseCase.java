package site.codecrew.account.application.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.codecrew.account.application.token.JsonWebTokenService;
import site.codecrew.account.domain.MemberService;
import site.codecrew.account.domain.SocialProfile;

@RequiredArgsConstructor
@Component
public class SocialAuthenticateUseCase {
    private final SocialAuthenticateResolver authenticateResolver;
    private final MemberService memberService;
    private final JsonWebTokenService jsonWebTokenService;

    public AuthenticatedTokenResult authenticate(SocialCredential socialCredential) {
        SocialProfile socialProfile = authenticateResolver.loadSocialProfile(socialCredential);

        return AuthenticatedTokenResult.from(memberService.findByMemberProfile(socialProfile)
            .map(jsonWebTokenService::issue)
            .orElseGet(() -> jsonWebTokenService.issue(socialProfile))
        );
    }
}
