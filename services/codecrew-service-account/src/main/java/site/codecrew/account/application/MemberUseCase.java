package site.codecrew.account.application;

import static java.util.Objects.requireNonNull;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import site.codecrew.account.application.token.JsonWebTokenClaims;
import site.codecrew.account.application.token.JsonWebTokenService;
import site.codecrew.account.application.token.PlainToken;
import site.codecrew.account.domain.MemberService;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;

@Slf4j
@RequiredArgsConstructor
@Component
public class MemberUseCase {

    private final JsonWebTokenService tokenService;
    private final MemberService memberService;

    public MemberResult findByToken(PlainToken accessToken) {
        JsonWebTokenClaims accessTokenClaims = tokenService.parse(accessToken);
        return MemberResult.from(
            memberService.findByPublicId(requireNonNull(accessTokenClaims.getClaimAsLong()))
        );
    }

    public MemberDuplicationCheckResult duplicationNameCheck(String nickname) {
        return memberService.findByNickname(nickname)
            .map(member -> MemberDuplicationCheckResult.ofDuplicated())
            .orElse(MemberDuplicationCheckResult.ofUnique());
    }
}
