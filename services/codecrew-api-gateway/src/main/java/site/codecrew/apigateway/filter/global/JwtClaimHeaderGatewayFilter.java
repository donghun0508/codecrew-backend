package site.codecrew.apigateway.filter.global;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import site.codecrew.apigateway.filter.global.JwtClaimHeaderGatewayFilter.Config;
import site.codecrew.apigateway.util.JwtClaims;

@Component
public class JwtClaimHeaderGatewayFilter extends AbstractGatewayFilterFactory<Config> {

    public JwtClaimHeaderGatewayFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> exchange.getPrincipal()
            .filter(JwtAuthenticationToken.class::isInstance)
            .cast(JwtAuthenticationToken.class)
            .flatMap(auth -> {
                var jwt = auth.getToken();

                String issuer = JwtClaims.issuer(jwt);
                String subject = JwtClaims.subject(jwt);
                String username = JwtClaims.username(jwt);
                String email = JwtClaims.email(jwt);

                var mutatedExchange = exchange.mutate()
                    .request(r -> r.headers(h -> {
                        removeAllHeaders(config, h);

                        setIssuerHeader(config, h, issuer);
                        setSubjectHeader(config, h, subject);
                        setUsernameHeader(config, h, username);
                        setEmailHeader(config, h, email);
                    }))
                    .build();

                return chain.filter(mutatedExchange);
            })
            .switchIfEmpty(chain.filter(exchange));
    }

    private void removeAllHeaders(Config config, HttpHeaders h) {
        if (config.isInjectIssuer())   h.remove(config.getIssuerHeader());
        if (config.isInjectSubject())  h.remove(config.getSubjectHeader());
        if (config.isInjectUsername()) h.remove(config.getUsernameHeader());
        if (config.isInjectEmail())    h.remove(config.getEmailHeader());
    }

    private void setIssuerHeader(Config config, HttpHeaders h, String issuer) {
        if (config.isInjectIssuer() && issuer != null) {
            h.set(config.getIssuerHeader(), issuer);
        }
    }

    private void setSubjectHeader(Config config, HttpHeaders h, String subject) {
        if (config.isInjectSubject() && subject != null) {
            h.set(config.getSubjectHeader(), subject);
        }
    }

    private void setUsernameHeader(Config config, HttpHeaders h, String username) {
        if (config.isInjectUsername() && username != null) {
            String encoded = URLEncoder.encode(username, StandardCharsets.UTF_8);
            h.set(config.getUsernameHeader(), encoded);
        }
    }

    private void setEmailHeader(Config config, HttpHeaders h, String email) {
        if (config.isInjectEmail() && email != null) {
            h.set(config.getEmailHeader(), email);
        }
    }

    @Getter
    @Setter
    public static class Config {
        private boolean injectIssuer = true;
        private boolean injectSubject = true;
        private boolean injectUsername = true;
        private boolean injectEmail = true;

        private String issuerHeader = "X-Issuer";
        private String subjectHeader = "X-Subject";
        private String usernameHeader = "X-Username";
        private String emailHeader = "X-Email";

        private String usernameClaim = "preferred_username";
    }
}