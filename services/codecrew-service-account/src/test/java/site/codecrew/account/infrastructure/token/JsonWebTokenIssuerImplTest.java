package site.codecrew.account.infrastructure.token;

import static org.assertj.core.api.Assertions.assertThat;
import static site.codecrew.account.service.JsonWebTokenPropertiesFixture.createDefault;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import site.codecrew.account.application.token.ClaimKey;
import site.codecrew.account.application.token.JsonWebToken;
import site.codecrew.account.application.token.JsonWebTokenClaims;
import site.codecrew.account.application.token.JsonWebTokenIssuer;
import site.codecrew.account.application.token.JsonWebTokenParser;
import site.codecrew.account.application.token.JsonWebTokenType;
import site.codecrew.account.application.token.PlainToken;
import site.codecrew.account.config.JsonWebTokenProperties;
import site.codecrew.account.config.JsonWebTokenProperties.TokenClaims;
import site.codecrew.account.config.JsonWebTokenProperties.TokenExpSeconds;

class JsonWebTokenIssuerImplTest {

    private JsonWebTokenIssuer issuer;

    private JsonWebTokenParser parser;

    @BeforeEach
    void setUp() {
        JsonWebTokenProperties properties = createDefault();
        issuer = new JsonWebTokenIssuerImpl(properties);
        parser = new JsonWebTokenParserImpl(properties);
    }

    @Test
    void issueTest() {
        // given
        JsonWebTokenClaims jsonWebTokenClaims = new JsonWebTokenClaims("sub",
            Map.of(
                ClaimKey.SOCIAL_TYPE, "GOOGLE",
                ClaimKey.EMAIL, "test@gmail.com",
                ClaimKey.NAME, "name",
                ClaimKey.PICTURE, "http://example.com/picture.jpg"
            )
        );

        // when
        JsonWebToken jsonWebToken = issuer.issue(JsonWebTokenType.TEMPORARY, jsonWebTokenClaims);

        // then
        PlainToken plainToken = PlainToken.from(jsonWebToken);
        JsonWebTokenClaims claims = parser.parse(plainToken);
        assertThat(claims.subject()).isEqualTo(jsonWebTokenClaims.subject());
        assertThat(claims.claims())
            .usingRecursiveComparison()
            .ignoringFields("TYPE")
            .isEqualTo(jsonWebTokenClaims.claims());
    }

}