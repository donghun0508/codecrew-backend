package site.codecrew.account.application.signup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import site.codecrew.account.application.auth.AuthenticatedTokenResult;
import site.codecrew.account.application.token.JsonWebTokenClaims;
import site.codecrew.account.application.token.JsonWebTokenService;
import site.codecrew.account.application.token.PlainToken;
import site.codecrew.account.domain.Member;
import site.codecrew.account.domain.MemberService;
import site.codecrew.account.domain.SignupAttributes;
import site.codecrew.account.domain.SocialProfile;

@RequiredArgsConstructor
@Component
public class SocialSignupUseCase {

    private final JsonWebTokenService jsonWebTokenService;
    private final MemberService memberService;

    @Transactional
    public AuthenticatedTokenResult signup(PlainToken plainToken, SignupAttributes attributes) {
        JsonWebTokenClaims temporaryTokenClaims = jsonWebTokenService.parseTemporaryToken(plainToken);
        Member member = memberService.create(SocialProfile.fromClaims(temporaryTokenClaims), attributes);
        return AuthenticatedTokenResult.from(jsonWebTokenService.issue(member));
    }
}
