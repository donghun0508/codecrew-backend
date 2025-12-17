package site.codecrew.account.application.token;


import site.codecrew.account.domain.Member;
import site.codecrew.account.domain.SocialProfile;

public interface JsonWebTokenProvider {

    SocialProfile parse(JsonWebToken token);

    JsonWebToken issue(Member member);
    JsonWebToken issue(SocialProfile socialProfile);
}
