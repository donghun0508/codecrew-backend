package site.codecrew.account.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.codecrew.account.application.token.JsonWebToken;
import site.codecrew.account.application.token.JsonWebTokenProvider;
import site.codecrew.account.domain.MemberService;
import site.codecrew.account.domain.SocialProfile;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;

@RequiredArgsConstructor
@Component
public class MemberUseCase {

    private final JsonWebTokenProvider tokenProvider;
    private final MemberService memberService;

    public MemberResult findByToken(JsonWebToken token) {
        SocialProfile profile = tokenProvider.parse(token);
        return MemberResult
            .from(memberService.findByMemberProfile(profile)
            .orElseThrow(() -> new CoreException(CoreErrorCode.NOT_FOUND)));
    }

    public MemberDuplicationCheckResult duplicationNameCheck(String nickname) {
        return null;
    }
}
