package site.codecrew.account.application.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.codecrew.account.application.token.JsonWebTokenProvider;
import site.codecrew.account.application.token.JsonWebTokenResult;
import site.codecrew.account.domain.MemberService;
import site.codecrew.account.domain.SocialProfile;

@RequiredArgsConstructor
@Component
public class SocialAuthenticateUseCase {
    private final SocialAuthenticateResolver authenticateResolver;
    private final MemberService memberService;
    private final JsonWebTokenProvider tokenProvider;

    public JsonWebTokenResult authenticate(SocialCredential socialCredential) {
        SocialProfile socialProfile = authenticateResolver.loadSocialProfile(socialCredential);
        return memberService.findByMemberProfile(socialProfile)
            .map(member -> JsonWebTokenResult.from(tokenProvider.issue(member)))
            .orElseGet(() -> JsonWebTokenResult.from(tokenProvider.issue(socialProfile)));
    }
}
