package site.codecrew.account.application.signup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import site.codecrew.account.application.token.JsonWebToken;
import site.codecrew.account.application.token.JsonWebTokenResult;
import site.codecrew.account.application.token.JsonWebTokenService;
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
    public JsonWebTokenResult signup(JsonWebToken temporaryToken, SignupAttributes attributes) {
        SocialProfile profile = jsonWebTokenService.parse(temporaryToken);
        Member member = memberService.create(profile, attributes);
        return JsonWebTokenResult.from(jsonWebTokenService.issue(member));
    }
}
