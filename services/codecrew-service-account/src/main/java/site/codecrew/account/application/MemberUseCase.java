package site.codecrew.account.application;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.codecrew.account.application.token.JsonWebTokenClaims;
import site.codecrew.account.application.token.JsonWebTokenService;
import site.codecrew.account.application.token.PlainToken;
import site.codecrew.account.domain.MemberService;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;

@RequiredArgsConstructor
@Component
public class MemberUseCase {

    private final JsonWebTokenService tokenService;
    private final MemberService memberService;

    public MemberResult findByToken(PlainToken accessToken) {
        JsonWebTokenClaims accessTokenClaims = tokenService.parse(accessToken);
        return MemberResult.from(memberService.findByPublicId(requireNonNull(accessTokenClaims.getClaimAsLong()))
            .orElseThrow(() -> new CoreException(CoreErrorCode.NOT_FOUND, "Member not found with id: " + accessTokenClaims.subject())));
    }

    public MemberDuplicationCheckResult duplicationNameCheck(String nickname) {
        return memberService.findByNickname(nickname)
            .map(member -> MemberDuplicationCheckResult.ofDuplicated())
            .orElse(MemberDuplicationCheckResult.ofUnique());
    }
}
