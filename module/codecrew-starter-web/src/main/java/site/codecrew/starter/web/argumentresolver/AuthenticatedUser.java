package site.codecrew.starter.web.argumentresolver;

public record AuthenticatedUser(String issuer, String subject, String username, String email) {

}