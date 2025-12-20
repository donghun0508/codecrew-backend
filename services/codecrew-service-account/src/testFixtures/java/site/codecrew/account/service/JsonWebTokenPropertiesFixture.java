package site.codecrew.account.service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import site.codecrew.account.config.JsonWebTokenProperties;
import site.codecrew.account.config.JsonWebTokenProperties.TokenClaims;
import site.codecrew.account.config.JsonWebTokenProperties.TokenExpSeconds;

public class JsonWebTokenPropertiesFixture {

    public static JsonWebTokenProperties createDefault() {
        JsonWebTokenProperties properties = new JsonWebTokenProperties();
        properties.setSecret(generateRandomSecret());

        TokenExpSeconds tokenExpSeconds = new TokenExpSeconds();
        tokenExpSeconds.setAccess(1000L);
        tokenExpSeconds.setTemporary(1000L);
        tokenExpSeconds.setRefresh(1000L);

        properties.setTokenExpSeconds(tokenExpSeconds);

        TokenClaims tokenClaims = new TokenClaims();
        tokenClaims.setIssuer("codecrew.site");
        tokenClaims.setAudience("codecrew-account-service");
        properties.setTokenClaims(tokenClaims);
        return properties;
    }

    private static String generateRandomSecret() {
        byte[] randomBytes = new byte[64]; // 64바이트 = 512비트
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getEncoder().encodeToString(randomBytes);
    }
}