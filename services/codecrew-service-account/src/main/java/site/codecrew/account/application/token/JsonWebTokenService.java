package site.codecrew.account.application.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.codecrew.account.application.token.AuthenticatedToken.Authenticated;
import site.codecrew.account.application.token.AuthenticatedToken.Pending;
import site.codecrew.account.domain.Member;
import site.codecrew.account.domain.SocialProfile;

@RequiredArgsConstructor
@Service
public class JsonWebTokenService {

    private final JsonWebTokenIssuer jsonWebTokenIssuer;
    private final JsonWebTokenParser jsonWebTokenParser;
    private final JsonWebTokenRepository jsonWebTokenRepository;

    public AuthenticatedToken issue(Member member) {
        JsonWebToken accessToken = jsonWebTokenIssuer.issue(JsonWebTokenType.ACCESS, JsonWebTokenClaims.of(member.publicId()));
        JsonWebToken refreshToken = jsonWebTokenIssuer.issue(JsonWebTokenType.REFRESH, JsonWebTokenClaims.of(member.publicId()));
        jsonWebTokenRepository.save(refreshToken);
        return new Authenticated(accessToken, refreshToken);
    }

    public AuthenticatedToken issue(SocialProfile socialProfile) {
        JsonWebToken temporaryToken = jsonWebTokenIssuer.issue(JsonWebTokenType.TEMPORARY, socialProfile.toClaims());
        return new Pending(temporaryToken);
    }

    public JsonWebTokenClaims parse(PlainToken plainToken) {
        return jsonWebTokenParser.parse(plainToken);
    }

    public JsonWebTokenClaims parseTemporaryToken(PlainToken plainToken) {
        return jsonWebTokenParser.parse(plainToken);
    }
}
