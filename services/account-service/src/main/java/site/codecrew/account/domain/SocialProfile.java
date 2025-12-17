package site.codecrew.account.domain;

public record SocialProfile(SocialType socialType, String sub, String email, String name, String picture) {

}
