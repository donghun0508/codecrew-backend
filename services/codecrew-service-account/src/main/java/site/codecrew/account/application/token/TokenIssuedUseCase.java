package site.codecrew.account.application.token;

import static java.util.Objects.requireNonNull;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.codecrew.account.application.auth.AuthenticatedTokenResult;
import site.codecrew.account.application.exception.AccountErrorCode;
import site.codecrew.account.application.exception.InvalidRefreshTokenException;
import site.codecrew.account.application.exception.TokenException;
import site.codecrew.account.domain.Member;
import site.codecrew.account.domain.MemberService;

@RequiredArgsConstructor
@Component
public class TokenIssuedUseCase {

    private final JsonWebTokenService jsonWebTokenService;
    private final MemberService memberService;

    public AuthenticatedTokenResult issue(PlainToken refreshToken) {
        JsonWebTokenClaims refreshTokenClaims = jsonWebTokenService.parse(refreshToken);
        JsonWebToken clientRefreshWebToken = refreshTokenClaims.toToken();
        JsonWebToken findJsonWebToken = jsonWebTokenService.findByTypeAndSubject(refreshToken.type(), clientRefreshWebToken.subject())
            .orElseThrow(() -> new InvalidRefreshTokenException(refreshToken));

        if(!findJsonWebToken.equals(clientRefreshWebToken)) {
            jsonWebTokenService.revokeRefreshSession(findJsonWebToken);
            throw new TokenException(AccountErrorCode.REFRESH_TOKEN_REUSED);
        }

        Member member = memberService.findByPublicId(
            requireNonNull(refreshTokenClaims.getClaimAsLong()));
        return AuthenticatedTokenResult.from(jsonWebTokenService.issue(member));
    }
}
