package site.codecrew.account.infrastructure.token;

import org.springframework.stereotype.Component;
import site.codecrew.account.application.token.JsonWebToken;
import site.codecrew.account.application.token.JsonWebTokenProvider;
import site.codecrew.account.domain.Member;
import site.codecrew.account.domain.SocialProfile;

@Component
class JsonWebTokenProviderImpl implements JsonWebTokenProvider {

    private final TokenIssuer tokenIssuer;

    public JsonWebTokenProviderImpl(TokenIssuer tokenIssuer) {
        this.tokenIssuer = tokenIssuer;
    }

    @Override
    public SocialProfile parse(JsonWebToken token) {
        return null;
    }

    @Override
    public JsonWebToken issue(Member member) {
        String token = tokenIssuer.issue(member);
        return JsonWebToken.access(token);
    }

    @Override
    public JsonWebToken issue(SocialProfile profile) {
        String token = tokenIssuer.issue(profile);
        return JsonWebToken.temporary(token);
    }

}