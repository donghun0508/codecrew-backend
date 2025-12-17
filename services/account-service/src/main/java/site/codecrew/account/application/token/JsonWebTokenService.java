package site.codecrew.account.application.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.codecrew.account.domain.Member;
import site.codecrew.account.domain.SocialProfile;

@RequiredArgsConstructor
@Service
public class JsonWebTokenService {

    private final JsonWebTokenProvider tokenProvider;

    public SocialProfile parse(JsonWebToken temporaryToken) {
        return tokenProvider.parse(temporaryToken);
    }

    public JsonWebToken issue(Member member) {
        return tokenProvider.issue(member);
    }
}
