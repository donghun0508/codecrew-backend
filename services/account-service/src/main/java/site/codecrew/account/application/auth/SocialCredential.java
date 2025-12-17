package site.codecrew.account.application.auth;


import site.codecrew.account.domain.SocialType;

public record SocialCredential(SocialType type, String authorizationCode, String nonce, String state) {

}
